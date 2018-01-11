package com.yibogame.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by parcool on 2016/5/31.
 */
public abstract class MyBaseAdapter<T, H> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    private LayoutInflater mLayoutInflater;

    public MyBaseAdapter(Context mContext, List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if (convertView == null) {
            convertView = buildConvertView(parent, mLayoutInflater);
            holder = buildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }
        bindViewData(holder, mList.get(position), position);

        return convertView;
    }

    /**
     * 建立convertView
     *
     * @param layoutInflater
     * @return
     */
    public abstract View buildConvertView(ViewGroup parent, LayoutInflater layoutInflater);

    /**
     * 建立视图Holder
     *
     * @param convertView
     * @return
     */
    public abstract H buildHolder(View convertView);

    /**
     * 绑定数据
     *
     * @param holder
     * @param t
     * @param position
     */
    public abstract void bindViewData(H holder, T t, int position);


}
