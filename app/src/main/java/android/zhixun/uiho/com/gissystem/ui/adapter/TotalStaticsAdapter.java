package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.TotalStaticsModel;

import java.util.List;

/**
 * Created by zp on 2016/12/1.
 */

public class TotalStaticsAdapter extends RecyclerView.Adapter<TotalStaticsAdapter.ViewHolder>{
    private Context context;
    private List<TotalStaticsModel> lists;

    public TotalStaticsAdapter(Context context, List<TotalStaticsModel> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public TotalStaticsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_statics,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(TotalStaticsAdapter.ViewHolder holder, int position) {
        TotalStaticsModel totalStaticsModel = lists.get(position);
        if (position % 4==0) {
            holder.tvContent.setText("成果类型");
        } else if(position %4==1){

            holder.tvContent.setText(totalStaticsModel.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
       public TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

