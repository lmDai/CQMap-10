package android.zhixun.uiho.com.gissystem.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

/**
 * Created by simple on 2018/1/17.
 */

public abstract class OnItemClickListener implements MultiItemTypeAdapter.OnItemClickListener {
    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
