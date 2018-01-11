package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CRModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;

import com.yibogame.util.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zp on 2016/12/6.
 */

public class LocalFileAdapter extends RecyclerView.Adapter<LocalFileAdapter.ViewHolder> {
    private Context context;
    private List<CompanyDetailByCheckedModel> lists;
    private OnItemClickListener mOnItemClickListener, mOnDeleteClickListener, mOnSubmitServerClickListener, mOnEditListener;

    public LocalFileAdapter(Context context, List<CompanyDetailByCheckedModel> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnDeleteClickListener(OnItemClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    //提交至本地服务器的监听
    public void setOnSubmitServerClickListener(OnItemClickListener mOnSubmitServerClickListener) {
        this.mOnSubmitServerClickListener = mOnSubmitServerClickListener;
    }

    public OnItemClickListener getmOnEditListener() {
        return mOnEditListener;
    }

    public void setmOnEditListener(OnItemClickListener mOnEditListener) {
        this.mOnEditListener = mOnEditListener;
    }

    @Override
    public LocalFileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalFileAdapter.ViewHolder holder, int position) {
        if (lists.size() > 0) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long now = lists.get(position).getSecrecyTime();
            holder.tvDateContent.setText(sDateFormat.format(new Date(now)));
            holder.tvCheckedUnitContent.setText(lists.get(position).getCompanyName());

            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDeleteClickListener.onItemClick(view, position);
                }
            });
            holder.tvExport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnSubmitServerClickListener.onItemClick(view, position);
                }
            });
            holder.tvEdit.setOnClickListener(v -> {
                mOnEditListener.onItemClick(v, position);
            });
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCheckedUnitContent, tvDateContent, tvDelete, tvExport, tvEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCheckedUnitContent = (TextView) itemView.findViewById(R.id.tv_checked_unit_content);
            tvDateContent = (TextView) itemView.findViewById(R.id.tv_date_content);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvExport = (TextView) itemView.findViewById(R.id.tv_export);
            tvEdit = (TextView) itemView.findViewById(R.id.tv_edit);
        }
    }
}
