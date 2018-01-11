package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.MainBottomModel;
import android.zhixun.uiho.com.gissystem.flux.models.MainBottomResultFFaModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/12/2.
 */

public class MainBottomResultFFaAdapter extends RecyclerView.Adapter<MainBottomResultFFaAdapter.ViewHolder> {
    private Context context;
    private List<MainBottomResultFFaModel> lists = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public MainBottomResultFFaAdapter(Context context, List<MainBottomResultFFaModel> lists) {
        this.context = context;
        this.lists = lists;
    }
//    cardView


    @Override
    public MainBottomResultFFaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_bttom_result_ffa, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainBottomResultFFaAdapter.ViewHolder holder, int position) {
        MainBottomResultFFaModel mainBottomResultFFaModel = lists.get(position);
        holder.tvompany.setText(mainBottomResultFFaModel.getCompany());
        holder.tvDate.setText(mainBottomResultFFaModel.getDate());
        holder.tvCode.setText(mainBottomResultFFaModel.getCode());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvompany, tvDate, tvCode;

        public ViewHolder(View itemView) {
            super(itemView);
            tvompany = (TextView) itemView.findViewById(R.id.tv_company);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvCode = (TextView) itemView.findViewById(R.id.tv_code_content);
        }
    }
}
