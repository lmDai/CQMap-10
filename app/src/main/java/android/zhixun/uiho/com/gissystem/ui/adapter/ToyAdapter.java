package android.zhixun.uiho.com.gissystem.ui.adapter;

/**
 * Created by parcool on 2016/12/20.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;

import java.util.List;

/**
 * Created by fish on 16/6/4.
 */
public class ToyAdapter extends RecyclerView.Adapter<ToyAdapter.FishViewHolder> {
    private List<String> data;
    private LayoutInflater inflater;

    public ToyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LogUtil.fish("onCreateViewHolder");
        FishViewHolder holder = new FishViewHolder(inflater.inflate(R.layout.toy_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FishViewHolder holder, int position) {
//        LogUtil.fish("onBindViewHolder " + position);
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<String> pDatas) {
        data = pDatas;
    }

    public class FishViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public FishViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
}
