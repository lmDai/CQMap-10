package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/12/2.
 */

public class UnitFilterAdapter extends RecyclerView.Adapter<UnitFilterAdapter.ViewHolder> {
    private Context context;
    private List<IndustryCategoryModel> lists = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public UnitFilterAdapter(Context context, List<IndustryCategoryModel> lists) {
        this.context = context;
        this.lists = lists;
    }
//    cardView


    @Override
    public UnitFilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unit_filter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitFilterAdapter.ViewHolder holder, int position) {
        IndustryCategoryModel unitFilterModel = lists.get(position);
        holder.tvContent.setText(unitFilterModel.getIndustryCategoryName());
        if (unitFilterModel.isChecked()) {
            holder.tvContent.setBackgroundColor(context.getResources().getColor(R.color.cardView));
            holder.tvContent.setTextColor(context.getResources().getColor(R.color.cardViewTextColorTrue));
        } else {
            holder.tvContent.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvContent.setTextColor(context.getResources().getColor(R.color.cardViewTextColorFalse));
        }

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
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }
}
