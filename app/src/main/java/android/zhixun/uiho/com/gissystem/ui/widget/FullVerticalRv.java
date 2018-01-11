package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.AttributeSet;

/**
 * Created by simple on 2018/1/11.
 */

public class FullVerticalRv extends RecyclerView {

    public FullVerticalRv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayoutManager(new FullyLinearLayoutManager(context));
        addItemDecoration(new DividerItemDecoration(context, VERTICAL));
    }
}
