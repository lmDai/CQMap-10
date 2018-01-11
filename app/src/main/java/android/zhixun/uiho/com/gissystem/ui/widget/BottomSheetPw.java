package android.zhixun.uiho.com.gissystem.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.util.ScreenUtil;

import com.yinglan.scrolllayout.ScrollLayout;
import com.yinglan.scrolllayout.content.ContentRecyclerView;

/**
 * Created by simple on 2018/1/12.
 */

public class BottomSheetPw extends PopupWindow {

    private Context mContext;
    private View mRootView;
    private ScrollLayout mScrollLayout;
    private ContentRecyclerView mContentRv;
    private View dragView;

    public BottomSheetPw(Context context) {
        super(context);
        mContext = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mRootView = View.inflate(context, R.layout.pw_bottom_sheet, null);
        setContentView(mRootView);
        setFocusable(false);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));
        initBottomDragView();
    }

    private void initBottomDragView() {
        mScrollLayout = mRootView.findViewById(R.id.scrollLayout);
        mContentRv = mRootView.findViewById(R.id.content_rv);
        mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight((Activity) mContext) * 0.5));
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(mContext, 50));
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit();

//        View root = findViewById(R.id.root);
//        root.setOnClickListener(v -> mScrollLayout.scrollToExit());
        dragView = mRootView.findViewById(R.id.bottom_drag_view);
        dragView.setOnClickListener(v -> mScrollLayout.setToOpen());
    }

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
//            if (text_foot.getVisibility() == View.VISIBLE)
//                text_foot.setVisibility(View.GONE);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
//                text_foot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    public void setAdapter(RecyclerView.Adapter bottomAdapter) {
        mContentRv.setLayoutManager(new LinearLayoutManager(mContext));
        mContentRv.setAdapter(bottomAdapter);
        mScrollLayout.setToOpen();
        dragView.setVisibility(View.VISIBLE);
    }
}
