package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simple on 2018/1/11.
 */

public class RvPopupWindow extends BasePopupWindow {

    private FullVerticalRv mRv;

    public RvPopupWindow(Context context, RvPwModel... models) {
        super(context);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mRv = findViewById(R.id.recycler_view);
        mRv.setAdapter(new RvPwAdapter(context, Arrays.asList(models)));
    }

    public RvPopupWindow(Context context, int[] resIds, String[] texts) {
        super(context);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mRv = findViewById(R.id.recycler_view);
        if (resIds.length != texts.length) {
            throw new IndexOutOfBoundsException();
        }
        List<RvPwModel> models = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            models.add(new RvPwModel(resIds[i], texts[i]));
        }
        mRv.setAdapter(new RvPwAdapter(context, models));
    }

    @Override
    public int getLayoutId() {
        return R.layout.pw_rv;
    }

    public static class RvPwModel {
        public int resId;
        public String text;

        public RvPwModel(int resId, String text) {
            this.resId = resId;
            this.text = text;
        }
    }

    public static class RvPwAdapter extends CommonAdapter<RvPwModel> {

        public RvPwAdapter(Context context, List<RvPwModel> datas) {
            super(context, R.layout.item_rv_pw, datas);
        }

        @Override
        protected void convert(ViewHolder holder, RvPwModel rvPwModel, int position) {
            ImageView imageView = holder.getView(R.id.image);
            TextView textView = holder.getView(R.id.text);

            imageView.setImageResource(rvPwModel.resId);
            textView.setText(rvPwModel.text);
        }

    }
}
