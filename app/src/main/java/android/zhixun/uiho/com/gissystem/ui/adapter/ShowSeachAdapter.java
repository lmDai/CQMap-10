package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class ShowSeachAdapter  extends RecyclerView.Adapter<ShowSeachAdapter.ViewHolder>{
    private List<CompanyDetailModel> lists;
    private Context context;
   private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ShowSeachAdapter(List<CompanyDetailModel> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public ShowSeachAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_result,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowSeachAdapter.ViewHolder holder, int position) {
      holder.tvContent.setText(lists.get(position).getCompanyName());
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
       public TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.companyContent);
        }
    }
}
