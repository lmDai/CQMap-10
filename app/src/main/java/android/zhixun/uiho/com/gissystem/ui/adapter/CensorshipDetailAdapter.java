package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CRImageModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitDetailModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.ui.widget.DoubleScaleImageView;

import com.bumptech.glide.Glide;


import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by zp on 2016/11/30.
 */

public class CensorshipDetailAdapter extends RecyclerView.Adapter<CensorshipDetailAdapter.ViewHolder>{
    private Context context;
    private List<String> lists;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CensorshipDetailAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public CensorshipDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unitdetail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CensorshipDetailAdapter.ViewHolder holder, int position) {
//        Uri uri = Uri.parse("res:///"+R.mipmap.bg_login);
//            holder.simpleDraweeView.setImageURI(uri);
        Glide.with(context)
                .load(lists.get(position))
                .into(holder.simpleDraweeView);

        if(mOnItemClickListener!=null){
            holder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
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
        public AppCompatImageView simpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (AppCompatImageView) itemView.findViewById(R.id.my_picture);
        }
    }

}
