package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by zp on 2016/11/30.
 */

public class ImageArrayAdapter extends RecyclerView.Adapter<ImageArrayAdapter.ViewHolder>{
    private Context context;
    private List<String> lists;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public ImageArrayAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public ImageArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unitdetail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageArrayAdapter.ViewHolder holder, int position) {
        Uri uri = Uri.parse(lists.get(position));
//            holder.simpleDraweeView.setImageURI(uri);

        Glide.with(context)
                .load(uri)
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
        public ImageView simpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (ImageView) itemView.findViewById(R.id.my_picture);
        }
    }

}
