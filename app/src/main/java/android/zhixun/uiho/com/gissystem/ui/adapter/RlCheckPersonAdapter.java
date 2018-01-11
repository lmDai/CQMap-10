package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.UserModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CheckUserModel;

import com.yibogame.util.LogUtil;

import java.util.List;

/**
 * Created by parcool on 2016/12/8.
 */

public class RlCheckPersonAdapter extends RecyclerView.Adapter<RlCheckPersonAdapter.ViewHolder> {

    private List<CheckUserModel> mList;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public RlCheckPersonAdapter(List<CheckUserModel> mList) {
        this.mList = mList;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rlcheckperson, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        if (onCheckedChangeListener != null) {
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    compoundButton.setTag(mList.get(viewHolder.getAdapterPosition()).getUserId());
                    onCheckedChangeListener.onCheckedChanged(compoundButton, b);
                }
            });
        }
        try {
            if (mList.get(viewHolder.getAdapterPosition()).isChecked()) {
                viewHolder.checkBox.setChecked(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.checkBox.setText(mList.get(position).getName());
        holder.checkBox.setChecked(mList.get(position).isChecked());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            this.checkBox = (CheckBox) itemView;
        }
    }
}
