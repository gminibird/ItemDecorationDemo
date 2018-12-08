package com.luwei.itemdecorationdemo.staggered;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/5
 */
public class StaggeredSpaceDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontal, mVertical, mLeft, mRight, mTop, mBottom;
    protected int mSpanCount;
    protected int mItemCount;

    /**
     * {@link #StaggeredSpaceDecoration(int, int, int, int, int, int)}
     */
    public StaggeredSpaceDecoration(int horizontal, int vertical) {
        this(horizontal, vertical, 0, 0);
    }

    /**
     * {@link #StaggeredSpaceDecoration(int, int, int, int, int, int)}
     */
    public StaggeredSpaceDecoration(int horizontal, int vertical, int left, int right) {
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
    public StaggeredSpaceDecoration(int horizontal, int vertical, int left, int right, int top, int bottom) {
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
        RecyclerView.LayoutManager originalManager = parent.getLayoutManager();
        if (originalManager == null || !(originalManager instanceof StaggeredGridLayoutManager)) {
            return;
        }
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) originalManager;
        mSpanCount = manager.getSpanCount();
        mItemCount = parent.getAdapter().getItemCount();
        if (manager.getOrientation() == StaggeredGridLayoutManager.VERTICAL) {
            handleVertical(outRect, view, parent);
        } else {
            handleHorizontal(outRect, view, parent);
        }
    }
    /**
     * orientation为Vertical时调用，处理Vertical下的Offset
     * {@link #getItemOffsets(Rect, View, RecyclerView, RecyclerView.State)}
     */
    private void handleVertical(@NonNull Rect outRect, @NonNull View view,
                                @NonNull RecyclerView parent) {
        StaggeredGridLayoutManager.LayoutParams params =
                (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = params.getSpanIndex();
        int adapterPos = parent.getChildAdapterPosition(view);
        int sizeAvg = (int) ((mHorizontal * (mSpanCount - 1) + mLeft + mRight) * 1f / mSpanCount);
        int left = computeLeft(spanIndex, sizeAvg);
        int right = computeRight(spanIndex, sizeAvg);
        outRect.left = left;
        outRect.right = right;
        outRect.top = mVertical / 2;
        outRect.bottom = mVertical / 2;
        if (isFirstRaw(adapterPos, spanIndex)) {
            //第一行
            outRect.top = mTop;
        }
        if (isLastRaw(spanIndex)) {
            //最后一行
            outRect.bottom = mBottom;
        }
    }

    /**
     * orientation为Horizontal时调用，处理Horizontal下的Offset
     * {@link #getItemOffsets(Rect, View, RecyclerView, RecyclerView.State)}
     */
    private void handleHorizontal(@NonNull Rect outRect, @NonNull View view,
                                  @NonNull RecyclerView parent) {
        StaggeredGridLayoutManager.LayoutParams params =
                (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = params.getSpanIndex();
        int adapterPos = parent.getChildAdapterPosition(view);
        int sizeAvg = (int) ((mVertical * (mSpanCount - 1) + mTop + mBottom) * 1f / mSpanCount);
        int top = computeTop(spanIndex, sizeAvg);
        int bottom = computeBottom(spanIndex, sizeAvg);
        outRect.top = top;
        outRect.bottom = bottom;
        outRect.left = mHorizontal / 2;
        outRect.right = mHorizontal / 2;
        if (isFirstRaw(adapterPos, spanIndex)) {
            outRect.left = mLeft;
        }
        if (isLastRaw(spanIndex)){
            outRect.right = mRight;
        }
    }


    /**
     * 判断Item是否是第一行
     * @param adapterPos item对应的Adapter position
     * @param spanIndex item对应的spanIndex
     */
    private boolean isFirstRaw(int adapterPos, int spanIndex) {
        return adapterPos < mSpanCount;
    }

    /**
     * 判断Item是否是第一行
     * @param spanIndex item对应的spanIndex
     */
    private boolean isLastRaw(int spanIndex) {
        return spanIndex >= mItemCount - mSpanCount;
    }

    /**
     * 计算Item的左边Decoration(outRect.left)尺寸,当orientation为Vertical时调用
     *
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg   每个Item需要填充的尺寸
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
     *
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg   每个Item需要填充的尺寸
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
     *
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg   每个Item需要填充的尺寸
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
     *
     * @param spanIndex 需要计算的Item对应的spanIndex
     * @param sizeAvg   每个Item需要填充的尺寸
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
}
