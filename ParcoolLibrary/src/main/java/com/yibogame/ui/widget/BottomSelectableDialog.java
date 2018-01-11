package com.yibogame.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yibogame.R;
import com.yibogame.ui.adapter.MyBaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanyi on 2017/3/29.
 */

public class BottomSelectableDialog extends Dialog {

    private RecyclerView mRecyclerView;
    private TextView mTvCancle;
    private List<Item> items;
    private Adapter adapter;
    private Builder builder;

    public BottomSelectableDialog(@NonNull Context context, Builder builder) {
        super(context, R.style.my_dialog);
        this.items = builder.getItem();
        this.builder = builder;
        init();
    }


    private void init() {
        LinearLayout root = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.dialog_bottom_selectable, null);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mTvCancle = (TextView) root.findViewById(R.id.tv_item);
        root.findViewById(R.id.tv_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSelectableDialog.this.dismiss();
            }
        });
        this.setContentView(root);
        Window dialogWindow = this.getWindow();
        if (dialogWindow == null) {
            Toast.makeText(getContext(), "getWindow is null!", Toast.LENGTH_SHORT).show();
            return;
        }
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
//        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
//        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        adapter = new Adapter(items, builder.getColor());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    public void notifyDatasetChanged() {
        adapter.notifyDataSetChanged();
    }

    private static class Item {
        private String name;
        private View.OnClickListener onClickListener;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private View.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }

    public static class Builder {
        private List<Item> items = new ArrayList<>();
        private int color;

        public int getColor() {
            return color;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public List<Item> getItem() {
            return items;
        }

        public void setItem(List<Item> item) {
            this.items.clear();
            this.items.addAll(item);
        }

        public Builder addItem(Item item) {
            this.items.add(item);
            return this;
        }

        public Builder addItem(String name, View.OnClickListener onClickListener) {
            Item item = new Item();
            item.setName(name);
            item.setOnClickListener(onClickListener);
            this.items.add(item);
            return this;
        }

        public BottomSelectableDialog build(Context context) {
            return new BottomSelectableDialog(context, this);
        }
    }


    private class Adapter extends MyBaseRecyclerViewAdapter<Item, ViewHolder> {
        private int color;

        public Adapter(List<Item> list, int color) {
            super(list);
            this.color = color;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(mList.get(position).getName());
            holder.textView.setOnClickListener(mList.get(position).getOnClickListener());
            if (color != 0) {
                holder.textView.setTextColor(color);
            }
        }

        @Override
        public int getItemLayoutResId() {
            return R.layout.item_dialog_bottom_selectable;
        }

        @Override
        public ViewHolder onCreateMyViewHolder(ViewGroup viewGroup, View itemView) {
            return new ViewHolder(itemView);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

}
