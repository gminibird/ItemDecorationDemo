package com.luwei.itemdecorationdemo.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/9/8
 */
public class TableDecoration extends GridSpaceDecoration {

    private Drawable mDivider;
    private int mSize;
    private Rect mBounds;

    /**
     * @param color 边框颜色
     * @param size 边框大小（px）
     */
    public TableDecoration(@ColorInt int color, int size) {
        super(size, size, size, size, size, size);
        mSize = size;
        mDivider = new ColorDrawable(color);
        mBounds = new Rect();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            draw(c, parent, view);
        }
//        drawLast(c, parent);
    }


    /**
     * 绘制View的边界
     * @param view 需要绘制的view
     */
    private void draw(Canvas canvas, RecyclerView parent, View view) {
        canvas.save();
        int translationX = Math.round(view.getTranslationX());
        int translationY = Math.round(view.getTranslationY());
        int viewLeft = view.getLeft() + translationX;
        int viewRight = view.getRight() + translationX;
        int viewTop = view.getTop() + translationY;
        int viewBottom = view.getBottom() + translationY;
        parent.getDecoratedBoundsWithMargins(view, mBounds);
        drawLeft(canvas, mBounds, viewLeft);
        drawRight(canvas, mBounds, viewRight);
        drawTop(canvas, mBounds, viewTop);
        drawBottom(canvas, mBounds, viewBottom);
        canvas.restore();
    }

    /**
     * 当最后一行没有充满时需要填充缺陷的地方
     */
    private void drawLast(Canvas canvas, RecyclerView parent) {
        View lastView = parent.getChildAt(parent.getChildCount() - 1);
        int pos = parent.getChildAdapterPosition(lastView);
        if (isLastColumn((GridLayoutManager.LayoutParams) lastView.getLayoutParams(),pos)){
            return;
        }
        int translationX = Math.round(lastView.getTranslationX());
        int translationY = Math.round(lastView.getTranslationY());
        int viewLeft = lastView.getLeft() + translationX;
        int viewRight = lastView.getRight() + translationX;
        int viewTop = lastView.getTop() + translationY;
        int viewBottom = lastView.getBottom() + translationY;
        parent.getDecoratedBoundsWithMargins(lastView, mBounds);
        canvas.save();
        if (mManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            int contentRight = parent.getRight() - parent.getPaddingRight() - Math.round(parent.getTranslationX());
            //空白区域上边缘
            mDivider.setBounds(mBounds.right, mBounds.top, contentRight, viewTop);
            mDivider.draw(canvas);
            //空白区域左边缘
            mDivider.setBounds(viewRight, viewTop, viewRight + mSize, mBounds.bottom);
            mDivider.draw(canvas);
        }else {
            int contentBottom = parent.getBottom()-parent.getPaddingBottom()-Math.round(parent.getTranslationY());
            //空白区域上边缘
            mDivider.setBounds(mBounds.left,viewBottom,mBounds.right,viewBottom+mSize);
            mDivider.draw(canvas);
            //空白区域左边缘
            mDivider.setBounds(mBounds.left,mBounds.bottom,viewLeft,contentBottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    /**
     * 画左边
     * @param bounds view包含decoration的边界
     *               {@link RecyclerView#getDecoratedBoundsWithMargins(View, Rect)}
     * @param left view的左边界
     */
    private void drawLeft(Canvas canvas, Rect bounds, int left) {
        mDivider.setBounds(bounds.left, bounds.top, left, bounds.bottom);
        mDivider.draw(canvas);
    }

    /**
     * 画右边
     * @param bounds view包含decoration的边界
     *               {@link RecyclerView#getDecoratedBoundsWithMargins(View, Rect)}
     * @param right view的右边界
     */
    private void drawRight(Canvas canvas, Rect bounds, int right) {
        mDivider.setBounds(right, bounds.top, bounds.right, bounds.bottom);
        mDivider.draw(canvas);
    }

    /**
     * 画右边
     * @param bounds view包含decoration的边界
     *               {@link RecyclerView#getDecoratedBoundsWithMargins(View, Rect)}
     * @param top view的上边界
     */
    private void drawTop(Canvas canvas, Rect bounds, int top) {
        mDivider.setBounds(bounds.left, bounds.top, bounds.right, top);
        mDivider.draw(canvas);
    }

    /**
     * 画右边
     * @param bounds view包含decoration的边界
     *               {@link RecyclerView#getDecoratedBoundsWithMargins(View, Rect)}
     * @param bottom view的下边界
     */
    private void drawBottom(Canvas canvas, Rect bounds, int bottom) {
        mDivider.setBounds(bounds.left, bottom, bounds.right, bounds.bottom);
        mDivider.draw(canvas);
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }
}
