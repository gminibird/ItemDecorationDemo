package com.luwei.itemdecorationdemo.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mr_Zeng
 * <p>
 * 透明间隔的网格Decoration,可以通过构造方法来设置相应位置的间隔
 * {@link #GridSpaceDecoration(int, int, int, int, int, int)
 * <p>
 * <p>
 * 可以通过继承该类来实现一些特殊的分割线效果
 * 由于此类已经写好{@link #getItemOffsets(Rect, View, RecyclerView, RecyclerView.State)}方法，所以只需
 * 继承构造方法并重写{@link #onDraw(Canvas, RecyclerView, RecyclerView.State)}即可实现特殊的分割线效果
 * <p>
 * 该Decoration目前只支持{@link GridLayoutManager }
 */
public class GridSpaceDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontal, mVertical, mLeft, mRight, mTop, mBottom;
    private boolean isFirst = true;
    protected GridLayoutManager mManager;
    protected int mSpanCount;
    protected int mChildCount;

    /**
     * @see #GridSpaceDecoration(int, int, int, int)
     */
    public GridSpaceDecoration(int horizontal, int vertical) {
        this(horizontal, vertical, 0, 0);
    }

    /**
     * @see #GridSpaceDecoration(int, int, int, int, int, int)
     */
    public GridSpaceDecoration(int horizontal, int vertical, int left, int right) {
        this(horizontal, vertical, left, right, 0, 0);
    }

    /**
     * @param horizontal 内部水平距离(px)
     * @param vertical   内部竖直距离(px)
     * @param left       最左边距离(px)，默认为0
     * @param right      最右边距离(px),默认为0
     * @param top        最顶端距离(px),默认为0
     * @param bottom     最底端距离(px),默认为0
     */
    public GridSpaceDecoration(int horizontal, int vertical, int left, int right, int top, int bottom) {
        mHorizontal = horizontal;
        mVertical = vertical;
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (isFirst) {
            init(parent);
            isFirst = false;
        }
        if (mManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            handleVertical(outRect, view, parent, state);
        } else {
            handleHorizontal(outRect, view, parent, state);
        }
    }

    /**
     * 计算Item的左边Decoration(outRect.left)尺寸,当orientation为Vertical时调用
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg 每个Item需要填充的尺寸
     * @return outRect.left
     */
    private int computeLeft(int spanIndex, int sizeAvg) {
        if (spanIndex == 0) {
            return mLeft;
        } else if (spanIndex >= mSpanCount / 2) {
            //从右边算起
            return sizeAvg - computeRight(spanIndex, sizeAvg);
        } else {
            //从左边算起
            return mHorizontal - computeRight(spanIndex - 1, sizeAvg);
        }
    }

    /**
     * 计算Item的右边Decoration(outRect.right)尺寸,当orientation为Vertical时调用
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg 每个Item需要填充的尺寸
     * @return outRect.right
     */
    private int computeRight(int spanIndex, int sizeAvg) {
        if (spanIndex == mSpanCount - 1) {
            return mRight;
        } else if (spanIndex >= mSpanCount / 2) {
            //从右边算起
            return mHorizontal - computeLeft(spanIndex + 1, sizeAvg);
        } else {
            //从左边算起
            return sizeAvg - computeLeft(spanIndex, sizeAvg);
        }
    }

    /**
     * 计算Item的顶部边Decoration(outRect.Top)尺寸,当orientation为Horizontal时调用
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg 每个Item需要填充的尺寸
     * @return outRect.top
     */
    private int computeTop(int spanIndex, int sizeAvg) {
        if (spanIndex == 0) {
            return mTop;
        } else if (spanIndex >= mSpanCount / 2) {
            //从底部算起
            return sizeAvg - computeBottom(spanIndex, sizeAvg);
        } else {
            //从顶端算起
            return mVertical - computeBottom(spanIndex - 1, sizeAvg);
        }
    }

    /**
     * 计算Item的底部Decoration(outRect.bottom)尺寸,当orientation为Horizontal时调用
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg 每个Item需要填充的尺寸
     * @return outRect.bottom
     */
    private int computeBottom(int spanIndex, int sizeAvg) {
        if (spanIndex == mSpanCount - 1) {
            return mBottom;
        } else if (spanIndex >= mSpanCount / 2) {
            //从底部算起
            return mVertical - computeTop(spanIndex + 1, sizeAvg);
        } else {
            //从顶端算起
            return sizeAvg - computeTop(spanIndex, sizeAvg);
        }
    }

    /**
     * orientation为Vertical时调用，处理Vertical下的Offset
     * {@link #getItemOffsets(Rect, View, RecyclerView, RecyclerView.State)}
     */
    private void handleVertical(Rect outRect, View view, RecyclerView parent,
                                RecyclerView.State state) {
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int childPos = parent.getChildAdapterPosition(view);
        int sizeAvg = (int) ((mHorizontal * (mSpanCount - 1) + mLeft + mRight) * 1f / mSpanCount);
        int spanSize = lp.getSpanSize();
        int spanIndex = lp.getSpanIndex();
        outRect.left = computeLeft(spanIndex, sizeAvg);
        if (spanSize == 0 || spanSize == mSpanCount) {
            outRect.right = sizeAvg - outRect.left;
        } else {
            outRect.right = computeRight(spanIndex + spanSize - 1, sizeAvg);
        }
        outRect.top = mVertical / 2;
        outRect.bottom = mVertical / 2;
        if (isFirstRaw(childPos)) {
            outRect.top = mTop;
        }
        if (isLastRaw(childPos)) {
            outRect.bottom = mBottom;
        }
    }

    /**
     * orientation为Horizontal时调用，处理Horizontal下的Offset
     * {@link #getItemOffsets(Rect, View, RecyclerView, RecyclerView.State)}
     */
    private void handleHorizontal(Rect outRect, View view, RecyclerView parent,
                                  RecyclerView.State state) {
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int childPos = parent.getChildAdapterPosition(view);
        int spanSize = lp.getSpanSize();
        int spanIndex = lp.getSpanIndex();
        int sizeAvg = (int) ((mVertical * (mSpanCount - 1) + mTop + mBottom) * 1f / mSpanCount);
        outRect.top = computeTop(spanIndex,sizeAvg);
        if (spanSize == 0 || spanSize == mSpanCount) {
            outRect.bottom = sizeAvg - outRect.top;
        } else {
            outRect.bottom = computeBottom(spanIndex + spanSize - 1, sizeAvg);
        }
        outRect.left = mHorizontal/2;
        outRect.right = mHorizontal/2;
        if (isFirstRaw(childPos)){
            outRect.left = mLeft;
        }
        if (isLastRaw(childPos)){
            outRect.right = mRight;
        }
    }

    /**
     * 初始化
     */
    private void init(RecyclerView parent) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (!(manager instanceof GridLayoutManager)) {
            throw new IllegalArgumentException("LayoutManger must instance of GridLayoutManager " +
                    "while using GridSpaceDecoration");
        }
        mManager = (GridLayoutManager) manager;
        mSpanCount = getSpanCount();
        mChildCount = parent.getAdapter().getItemCount();
    }

    protected int getSpanIndex(int pos) {
        int spanIndex;
        GridLayoutManager.SpanSizeLookup lookup = mManager.getSpanSizeLookup();
        lookup.setSpanIndexCacheEnabled(true);
        spanIndex = lookup.getSpanIndex(pos, mSpanCount);
        return spanIndex;
    }

    protected int getSpanCount() {
        return mManager.getSpanCount();
    }

    protected boolean isFirstColumn(GridLayoutManager.LayoutParams params, int pos) {
        return params.getSpanIndex() == 0;
    }

    protected boolean isFirstRaw(int pos) {
        if (mChildCount <= 0) {
            return false;
        }
        GridLayoutManager.SpanSizeLookup lookup = mManager.getSpanSizeLookup();
        return lookup.getSpanGroupIndex(pos, mSpanCount) == lookup.getSpanGroupIndex(0, mSpanCount);
    }

    protected boolean isLastRaw(int pos) {
        if (mChildCount <= 0) {
            return false;
        }
        GridLayoutManager.SpanSizeLookup lookup = mManager.getSpanSizeLookup();
        return lookup.getSpanGroupIndex(pos, mSpanCount) == lookup.getSpanGroupIndex(mChildCount - 1, mSpanCount);
    }

    protected boolean isLastColumn(GridLayoutManager.LayoutParams params, int pos) {
        int index = params.getSpanIndex();
        int size = params.getSpanSize();
        return index + size == mSpanCount;
    }


    public int getHorizontal() {
        return mHorizontal;
    }

    public void setHorizontal(int mHorizontal) {
        this.mHorizontal = mHorizontal;
    }

    public int getVertical() {
        return mVertical;
    }

    public void setVertical(int mVertical) {
        this.mVertical = mVertical;
    }

    public int getLeft() {
        return mLeft;
    }

    public void setLeft(int mLeft) {
        this.mLeft = mLeft;
    }

    public int getRight() {
        return mRight;
    }

    public void setRight(int mRight) {
        this.mRight = mRight;
    }

    public int getTop() {
        return mTop;
    }

    public void setmTop(int mTop) {
        this.mTop = mTop;
    }

    public int getmBottom() {
        return mBottom;
    }

    public void setmBottom(int mBottom) {
        this.mBottom = mBottom;
    }
}
