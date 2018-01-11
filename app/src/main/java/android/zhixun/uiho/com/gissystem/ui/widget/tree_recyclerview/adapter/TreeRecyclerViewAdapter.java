package android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.adapter;

/**
 * Created by parcool on 2016/12/19.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.ViewHolder;

import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeRecyclerViewAdapter<T extends TreeAdapterItem> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<T> mNodes;//处理后的展示数据

    /***
     * 储存所有的展开的子item
     */
    private int sonItem = 0;
    /***
     * 存储位置比sonItem晓得MAP
     */


    private List<Integer> mListMap = new ArrayList<>();

    /**
     * 点击item的回调接口
     */
    private OnTreeItemClickListener onTreeItemClickListener;
    private OnTreeDetailClickListener onTreeDetailClickListener;

    public void setOnTreeItemClickListener(OnTreeItemClickListener onTreeItemClickListener) {
        this.onTreeItemClickListener = onTreeItemClickListener;
    }

    public void setOnTreeDetailClickListener(OnTreeDetailClickListener onTreeDetailClickListener) {
        this.onTreeDetailClickListener = onTreeDetailClickListener;
    }

    /**
     * @param context
     * @param datas
     * @param headerCount 如果是有刷新功能的那个XRecyclerView就设置他的headerCount（headerCount指的是XRecyclerView上面的那个刷新的View，一个View算一个，如果没有手动为XRecyclerView添加header，那么它有且只有一个：刷新状态的View）。如果是普通的RecyclerView，那么就是0
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeRecyclerViewAdapter(Context context, List<T> datas, int headerCount) {
        mContext = context;
        mNodes = datas;
        this.headerCount = headerCount;
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position 触发的条目
     */
    public void expandOrCollapse(int position) {
        TreeAdapterItem treeAdapterItem = mNodes.get(position);
        if (!treeAdapterItem.isParent()) {
            return;
        }
        boolean expand = treeAdapterItem.isExpand();
        if (expand) {
            mNodes.removeAll(treeAdapterItem.getAllChilds());
            treeAdapterItem.onCollapse();
        } else {
            mNodes.addAll(position + 1, treeAdapterItem.getChilds());
            treeAdapterItem.onExpand();
        }
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    TreeAdapterItem treeAdapterItem = mNodes.get(position);
                    if (treeAdapterItem.getSpanSize() == 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return treeAdapterItem.getSpanSize();
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, viewType);
    }

    private int headerCount = 1;

    public int getHeaderCount() {
        return headerCount;
    }

    //    private TreeAdapterItem mTreeAdapterItem;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            int holderPosition = holder.getAdapterPosition() - headerCount;

            @Override
            public void onClick(View v) {
//                LogUtil.e("onItemViewClick-:holderPosition->" + holderPosition + ",position->" + position);
                expandOrCollapse(holderPosition);
                if (onTreeItemClickListener != null) {
                    if (holderPosition < mNodes.size()) {
                        onTreeItemClickListener.onClick(mNodes.get(holderPosition), holderPosition);
                    } else {
                        ToastUtil.showShort("holderPosition>mNodes.size()" + ",holderPosition=" + holderPosition + ",mNodes.size()=" + mNodes.size());
                    }
                }

            }
        });
        if (holder.itemView.findViewById(R.id.tv_details) != null) {
            holder.setOnClickListener(R.id.tv_details, new View.OnClickListener() {
                int holderPosition = holder.getAdapterPosition() - headerCount;

                @Override
                public void onClick(View view) {
//                    LogUtil.e("onDetailClick-:holderPosition->" + holderPosition + ",position->" + position);
                    if (onTreeDetailClickListener != null) {
                        onTreeDetailClickListener.onClick(mNodes.get(holderPosition), holderPosition, sonItem, mListMap, mNodes.size());//将字item的个数传出去
                    }
                }
            });
        }
//        LogUtil.e("->>>>>>>>>>holder.getAdapterPosition() - headerCount="+(holder.getAdapterPosition() - headerCount));
        mNodes.get(holder.getAdapterPosition() - headerCount).onBindViewHolder(holder);
    }

    @Override
    public int getItemViewType(int position) {
        if (mNodes.size() == 0) {
            return 0;
        }
        return mNodes.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mNodes == null ? 0 : mNodes.size();
    }

    public interface OnTreeItemClickListener {
        void onClick(TreeAdapterItem node, int position);
    }

    public interface OnTreeDetailClickListener {
        void onClick(TreeAdapterItem node, int position, int sonItem, List<Integer> mListMap, int tatol);
    }
}