package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/12/2.
 */

public class MainBottomAdapter extends RecyclerView.Adapter<MainBottomAdapter.ViewHolder> {
    private Context context;
    private List<CompanyDetailModel> lists = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener mOnItemClickListenerDetail;

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public OnItemClickListener getOnItemClickListenerDetail() {
        return mOnItemClickListenerDetail;
    }

    public void setOnItemClickListenerDetail(OnItemClickListener mOnItemClickListenerDetail) {
        this.mOnItemClickListenerDetail = mOnItemClickListenerDetail;
    }

    public MainBottomAdapter(Context context, List<CompanyDetailModel> lists) {
        this.context = context;
        this.lists = lists;
    }
//    cardView


    @Override
    public MainBottomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_bttom, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rlIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            }
        });
        viewHolder.rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //暂时与点击intro的执行动作一样
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            }
        });
        viewHolder.rlDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            }
        });
//        viewHolder.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainBottomAdapter.ViewHolder holder, int position) {
//        for (CompanyDetailModel list : lists) {
//            LogUtil.e("--->"+list.getCompanyName());
//        }
        CompanyDetailModel mainBottomModel = lists.get(position);
        holder.tvompany.setText(mainBottomModel.getCompanyName());
        holder.tvAddress.setText(mainBottomModel.getCompanyAddre());
        holder.tvDistance.setText("1000");
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvompany, tvDistance, tvAddress;
        RelativeLayout rlIntro, rlLocation, rlDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvompany = (TextView) itemView.findViewById(R.id.tv_company);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            rlIntro = (RelativeLayout) itemView.findViewById(R.id.rl_intro);
            rlLocation = (RelativeLayout) itemView.findViewById(R.id.rl_location);
            rlDetail = (RelativeLayout) itemView.findViewById(R.id.rl_detail);
        }
    }
}
