package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author zhy
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int spanCount;

    public DividerGridItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

//    public void drawHorizontal(Canvas c, RecyclerView parent) {
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
//                    .getLayoutParams();
//            final int left = child.getLeft() - params.leftMargin;
//            final int right = child.getRight() + params.rightMargin
//                    + mDivider.getIntrinsicWidth();
//            final int top = child.getBottom() + params.bottomMargin;
//            final int bottom = top + mDivider.getIntrinsicHeight();
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }

    private boolean showFirstLine = true;

    public boolean isShowFirstLine() {
        return showFirstLine;
    }

    public void setShowFirstLine(boolean showFirstLine) {
        this.showFirstLine = showFirstLine;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin - mDivider.getIntrinsicWidth();
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            int top;
            int bottom;
            if ((i / spanCount) == 0) {
                //画item最上面的分割线
                top = 0;
                bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                //画item下面的分割线
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else {
                //画item下面的分割线
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }

        }
    }

//    public void drawVertical(Canvas c, RecyclerView parent) {
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
//                    .getLayoutParams();
//            final int top = child.getTop() - params.topMargin;
//            final int bottom = child.getBottom() + params.bottomMargin;
//            final int left = child.getRight() + params.rightMargin;
//            final int right = left + mDivider.getIntrinsicWidth();
//
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        if (spanCount == 0) {
            spanCount = getSpanCount(parent);
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            int left;
            int right;
            if (!showFirstLine && (i == 0)) {// || i == spanCount - 1   //不用这个了，因为第一行只有1个item的话。。。比如：ExpandableListView
                continue;
            }
            if ((i % spanCount) == 0) {
                //item左边分割线
                left = 0;
                right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                //item右边分割线
                left = child.getRight() + params.rightMargin;
                right = left + mDivider.getIntrinsicWidth();

            } else {
                left = child.getRight() + params.rightMargin;
                right = left + mDivider.getIntrinsicWidth();
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRow(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFirstRow(RecyclerView parent, int childPosition) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (childPosition < spanCount) {
                return true;
            }
        }
        return false;
    }

    private boolean isFirstColumn(RecyclerView parent, int childPosition) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (childPosition % spanCount == 0) {
                return true;
            }
//            childCount = childCount - childCount % spanCount;
//            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
//                return true;
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (spanCount == 0) {
            spanCount = getSpanCount(parent);
        }
        int childCount = parent.getAdapter().getItemCount();
//        if (isLastRow(parent, parent.getChildAdapterPosition(view), spanCount, childCount)) {// 如果是最后一行，则不需要绘制底部
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//        } else if (isLastColumn(parent, parent.getChildAdapterPosition(view), spanCount, childCount)) {// 如果是最后一列，则不需要绘制右边
//            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//        } else if (isFirstRow(parent, parent.getChildAdapterPosition(view))) {//如果是第一行
//            if (isFirstColumn(parent, parent.getChildAdapterPosition(view))) {//如果是第一列
//
//            } else {
//
//            }
//        } else if (isFirstColumn(parent, parent.getChildAdapterPosition(view))) {//如果是第一列
//            if (isFirstRow(parent, parent.getChildAdapterPosition(view))) {//如果是的第一行
//
//            } else {
//
//            }
//
//        } else {
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(),
//                    mDivider.getIntrinsicHeight());
//        }

        if (isFirstRow(parent, parent.getChildAdapterPosition(view)) || isFirstColumn(parent, parent.getChildAdapterPosition(view))) {
            if (isFirstRow(parent, parent.getChildAdapterPosition(view))) {//如果是第一行
                if (isFirstColumn(parent, parent.getChildAdapterPosition(view))) {//如果是第一列
                    outRect.set(mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
                } else {
                    //第一行的非第一列
                    outRect.set(0, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
                }
            } else {
                //如果不是第一行，那么肯定是第一列且不是第一行
                outRect.set(mDivider.getIntrinsicWidth(), 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
            }
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }

    }
}