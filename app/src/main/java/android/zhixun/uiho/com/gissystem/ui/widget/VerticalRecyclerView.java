package android.zhixun.uiho.com.gissystem.ui.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by parcool on 2016/7/21.
 */

public class VerticalRecyclerView extends XRecyclerView {

    public VerticalRecyclerView(Context context) {
        this(context,null);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.setLayoutManager(linearLayoutManager);
        //设置Item增加、移除动画
        this.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        this.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }
}
