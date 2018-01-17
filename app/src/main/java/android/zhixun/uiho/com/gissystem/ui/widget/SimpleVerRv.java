package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by simple on 2018/1/17.
 */

public class SimpleVerRv extends RecyclerView {
    public SimpleVerRv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(context));
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .color(Color.GRAY)
                .showLastDivider()
                .size(2)
                .build());
    }


}
