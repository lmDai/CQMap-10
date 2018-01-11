package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;

import java.util.List;

/**
 * Created by zp on 2016/12/23.
 */

public class OwnDetailDialogAdapter extends RecyclerView.Adapter<OwnDetailDialogAdapter.ViewHolder> {
    private Context context;
    private List<String> lists;

    public OwnDetailDialogAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public OwnDetailDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_own_dialog, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnDetailDialogAdapter.ViewHolder holder, int position) {
        holder.tvContent.setText(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
