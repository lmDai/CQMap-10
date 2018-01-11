package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CRImageModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;


import com.bumptech.glide.Glide;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import java.util.List;

/**
 * Created by zp on 2016/12/6.
 */

public class CensorshipRegisterAdapter extends RecyclerView.Adapter<CensorshipRegisterAdapter.ViewHolder> {
    private Context context;
    private List<CRImageModel> lists;
    private OnItemClickListener mOnItemClickListener, mOnItemDeleteListener;

    public void setOnItemDeleteListener(OnItemClickListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CensorshipRegisterAdapter(Context context, List<CRImageModel> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public CensorshipRegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_censorship_register, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CensorshipRegisterAdapter.ViewHolder holder, int position) {
//        LogUtil.e("path=========>"+lists.get(position).getPath());
        Uri uri = Uri.parse(position == 0 ? lists.get(position).getPath() : ContentResolver.SCHEME_FILE + "://" + lists.get(position).getPath());
        if (position == 0) {
            holder.aciDelete.setVisibility(View.GONE);
            holder.imageView.setImageURI(uri);
            Glide.with(context)
                    .load(uri)
                    .into(holder.imageView);
            LogUtil.e("为0的list的位置的路径----------------》" + lists.get(0).getPath());
        } else {
            try {
                holder.aciDelete.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(lists.get(position).getRemotePath())){
                    uri = Uri.parse(lists.get(position).getRemotePath());
                }
                holder.imageView.setImageURI(uri);
                Glide.with(context)
                        .load(uri)
                        .into(holder.imageView);
                LogUtil.e("为" + position + "的list的位置的路径----------------》" + lists.get(position).getPath());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if (mOnItemDeleteListener != null) {
            holder.aciDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemDeleteListener.onItemClick(view, holder.getAdapterPosition());
                }
            });
        }

        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView imageView, aciDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.my_picture);
            aciDelete = (AppCompatImageView) itemView.findViewById(R.id.aci_delete);
        }
    }
}
