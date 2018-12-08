package com.luwei.itemdecorationdemo.stick;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/10/15
 */
public class StickHeaderDecoration extends RecyclerView.ItemDecoration {

    //存储粘性头部的ViewHolder实例，复用机制减少性能损耗
    private SparseArray<RecyclerView.ViewHolder> mViewMap = new SparseArray<>();
    //StickHeader的提供者
    private StickProvider mProvider;

    public StickHeaderDecoration(StickProvider provider) {
        mProvider = provider;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
                           @NonNull RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null || !(adapter instanceof StickProvider)) {
            return;
        }
        int itemCount = adapter.getItemCount();
        if (itemCount == 1) {
            return;
        }
        //找到当前的StickHeader对应的position
        int currStickPos = currStickPos(parent);
        if (currStickPos == -1) {
            return;
        }
        c.save();
        if (parent.getClipToPadding()) {
            //考虑padding的情况
            c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(),
                    parent.getWidth() - parent.getPaddingRight(),
                    parent.getHeight() - parent.getPaddingBottom());
        }
        int currStickType = adapter.getItemViewType(currStickPos);
        //当前显示的StickHeader相应的ViewHolder，先看有没有缓存
        RecyclerView.ViewHolder currHolder = mViewMap.get(currStickType);
        if (currHolder == null) {
            //没有缓存则新生成
            currHolder = adapter.createViewHolder(parent, currStickType);
            //主动测量并布局
            measure(currHolder.itemView, parent);
            mViewMap.put(currStickType, currHolder);
        }
        //寻找下一个StickHeader
        RecyclerView.ViewHolder nextStickHolder = nextStickHolder(parent, currStickPos);
        if (nextStickHolder != null) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) currHolder.itemView.getLayoutParams();
            int bottom = parent.getPaddingTop() + params.topMargin + currHolder.itemView.getMeasuredHeight();
            int nextStickTop = nextStickHolder.itemView.getTop();
            //下一个StickHeader如果顶部碰到了当前StickHeader的屁股，那么将当前的向上推
            if (nextStickTop < bottom && nextStickTop > 0) {
                c.translate(0, nextStickTop - bottom);
            }
        }
        adapter.bindViewHolder(currHolder, currStickPos);
        c.translate(currHolder.itemView.getLeft(), currHolder.itemView.getTop());
        currHolder.itemView.draw(c);
        c.restore();
    }


    /**
     * 寻找当前的应该显示的StickHeader
     *
     * @param parent
     * @return
     */
    private int currStickPos(RecyclerView parent) {
        int childCount = parent.getChildCount();
        int paddingTop = parent.getPaddingTop();
        int currStickPos = -1;
        for (int i = 0; i < childCount; i++) {
            //考虑到parent padding 的情况，第一个item有可能不可见情况
            //从第1个child向后找
            View child = parent.getChildAt(i);
            if (child.getTop() >= paddingTop) {
                break;
            }
            int pos = parent.getChildAdapterPosition(child);
            if (mProvider.isStick(pos)) {
                currStickPos = pos;
            }
        }
        if (currStickPos != -1) {
            return currStickPos;
        }
        for (int i = parent.getChildAdapterPosition(parent.getChildAt(0)) - 1; i >= 0; i--) {
            //从第一个child的前一个开始找
            if (mProvider.isStick(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 下一个StickHeader的ViewHolder
     */
    private RecyclerView.ViewHolder nextStickHolder(RecyclerView parent, int currPos) {
        int childCount = parent.getChildCount();
        if (childCount == 1) {
            return null;
        }
        for (int i = 1; i < childCount; i++) {
            //从RecyclerView第二个Child开始找
            View child = parent.getChildAt(i);
            int childPos = parent.getChildAdapterPosition(child);
            if (mProvider.isStick(childPos) && childPos > currPos) {
                return parent.getChildViewHolder(child);
            }
        }
        return null;
    }

    /**
     * 手动Measure并Layout
     */
    public void measure(View header, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) header.getLayoutParams();
        int heightSpec;
        int widthSpec;
        //测量高
        if (params.height == RecyclerView.LayoutParams.MATCH_PARENT) {
            int height = parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom();
            heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        } else if (params.height == RecyclerView.LayoutParams.WRAP_CONTENT) {
            heightSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        } else {
            int height = params.height;
            heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        }
        //测量宽
        if (params.width == RecyclerView.LayoutParams.MATCH_PARENT) {
            int width = parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight();
            widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        } else if (params.width == RecyclerView.LayoutParams.WRAP_CONTENT) {
            widthSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        } else {
            int width = params.width;
            widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        }
        header.measure(widthSpec, heightSpec);
        int left = parent.getPaddingLeft() + params.leftMargin;
        int top = parent.getPaddingTop() + params.topMargin;

        header.layout(left,
                top,
                left + header.getMeasuredWidth(),
                top + header.getMeasuredHeight());
    }

    private void translate(View view, int offset) {
        view.setTop(view.getTop() + offset);
        view.setBottom(view.getBottom() + offset);
    }

    private void layout(View header, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) header.getLayoutParams();
        int left = parent.getPaddingLeft() + params.leftMargin;
        int top = parent.getPaddingTop() + params.topMargin;
        header.setLeft(left);
        header.setRight(left + header.getMeasuredWidth());
        header.setTop(top);
        header.setBottom(top + header.getMeasuredHeight());
    }

    public interface StickProvider {
        boolean isStick(int position);
    }

}
