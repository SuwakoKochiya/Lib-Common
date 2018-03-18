package com.chhd.android.common.ui.view.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author : 葱花滑蛋
 * date   : 2017/11/21
 * desc   :
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int space; // item与item之间的边距
    private int orientation = VERTICAL;

    private int left; // item与父容器的左边距
    private int top; // 第一个item与父容器的上边距
    private int right;// item与如容器的右边距
    private int bottom; // 最后一个item与父容器的底边距

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    public SpaceItemDecoration(int space, int orientation) {
        this.space = space;
        this.orientation = orientation;
    }

    public SpaceItemDecoration(int space, int orientation, int left, int top, int right, int bottom) {
        this.space = space;
        this.orientation = orientation;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == VERTICAL) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(left, top, right, 0);
            } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.set(left, space, right, bottom);
            } else {
                outRect.set(left, space, right, 0);
            }
        } else {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(left, top, 0, bottom);
            } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.set(space, top, right, bottom);
            } else {
                outRect.set(space, top, 0, bottom);
            }
        }
    }

}