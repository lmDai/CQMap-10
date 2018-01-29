package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/12/2.
 */

public class UnitFilterUnitAdapter extends RecyclerView.Adapter<UnitFilterUnitAdapter.ViewHolder> {
    private Context context;
    private List<AreaModel> lists = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public UnitFilterUnitAdapter(Context context, List<AreaModel> lists) {
        this.context = context;
        this.lists = lists;
    }
//    cardView


    @Override
    public UnitFilterUnitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unit_filter_unit, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitFilterUnitAdapter.ViewHolder holder, int position) {
        AreaModel unitFilterUnitModel = lists.get(position);

        holder.tvContentUnit.setText(unitFilterUnitModel.getAreaName());

        if (unitFilterUnitModel.isChecked()) {
            holder.tvContentUnit.setBackgroundColor(ContextCompat.getColor(context, R.color.ee7559));
            holder.tvContentUnit.setTextColor(ContextCompat.getColor(context, R.color.cardViewTextColorTrue));
        } else {
            holder.tvContentUnit.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
            holder.tvContentUnit.setTextColor(ContextCompat.getColor(context, R.color.cardViewTextColorFalse));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContentUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContentUnit = (TextView) itemView.findViewById(R.id.tv_content_unit);
            tvContentUnit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }
}
