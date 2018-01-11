package com.yibogame.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by tanyi on 2017/3/29.
 */
public abstract class MyBaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

//    private OnItemClickListener mOnItemClickListener;
    protected List<T> mList;
    protected int mHeadersCount;

//    public int getHeadersCount() {
//        return mHeadersCount;
//    }
//
//    public void setHeadersCount(int headersCount) {
//        this.mHeadersCount = headersCount;
//    }

    public MyBaseRecyclerViewAdapter(List<T> list) {
        this.mList = list;
    }

//    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }

    public abstract int getItemLayoutResId();

    public abstract VH onCreateMyViewHolder(ViewGroup viewGroup, View itemView);


    /***
     * 这个方法其实一般没什么情况下会用到
     *
     * @return
     */
//    @Deprecated
//    public OnItemClickListener getOnItemClickListener() {
//        return mOnItemClickListener;
//    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutResId(), parent, false);
        final VH vh = onCreateMyViewHolder(parent, itemView);
        if (parent instanceof XRecyclerView) {
            mHeadersCount = ((XRecyclerView) parent).getWrapAdapter().getHeadersCount();
        }
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(v, vh.getAdapterPosition() - mHeadersCount);
//                }
//            }
//        });
        return vh;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
