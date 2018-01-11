package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.zhixun.uiho.com.gissystem.R;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simple on 2018/1/11.
 */

public class SpaceDialog {

    View contentView;
    FullVerticalRv mRv;
    Context mContext;
    RvPopupWindow.RvPwAdapter mAdapter;
    OnItemClickListener mOnItemClickListener;

    public SpaceDialog(Context context, ViewGroup viewGroup) {
        mContext = context;
        contentView = LayoutInflater
                .from(context)
                .inflate(R.layout.pw_rv, viewGroup,
                        false);


        mRv = contentView.findViewById(R.id.recycler_view);

    }

    public SpaceDialog setData(int[] resIds, String[] texts) {
        if (resIds.length != texts.length) {
            throw new IndexOutOfBoundsException();
        }
        List<RvPopupWindow.RvPwModel> models = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            models.add(new RvPopupWindow.RvPwModel(resIds[i], texts[i]));
        }
        mAdapter = new RvPopupWindow.RvPwAdapter(mContext, models);
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                    dismisss();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return this;
    }

    public SpaceDialog show(View view) {
        DialogUtil.getInstance().showAnchorDialog(contentView, view);
        return this;
    }

    public void dismisss() {
        DialogUtil.getInstance().dismiss();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
