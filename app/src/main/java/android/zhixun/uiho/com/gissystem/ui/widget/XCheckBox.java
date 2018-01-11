package android.zhixun.uiho.com.gissystem.ui.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.zhixun.uiho.com.gissystem.R;


/**
 * Created by parcool on 2016/11/24.
 */

public class XCheckBox extends ImageView {

    private OnCheckedChangeListener mOnCheckedChangeListener;
    private XCheckBoxState mXCheckBoxState;

    public XCheckBox(Context context) {
        this(context, null);
    }

    public XCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode()){
            return;
        }
        this.setXCheckBoxState(XCheckBoxState.NO_CHECKED);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCheckedChangeListener != null) {
                    if (mXCheckBoxState == XCheckBoxState.CHECKED_ALL) {
                        //如果之前是选中全部的，设置为未选中
                        mOnCheckedChangeListener.onCheckedChanged(XCheckBox.this, XCheckBoxState.NO_CHECKED);
                        setXCheckBoxState(XCheckBoxState.NO_CHECKED);
                    } else if (mXCheckBoxState == XCheckBoxState.NO_CHECKED) {
                        //如果之前是未选中的，设置为全选
                        mOnCheckedChangeListener.onCheckedChanged(XCheckBox.this, XCheckBoxState.CHECKED_ALL);
                        setXCheckBoxState(XCheckBoxState.CHECKED_ALL);
                    } else if (mXCheckBoxState == XCheckBoxState.CHECKED_PART) {
                        //如果之前是选中一部分的，设置为选中为全选
                        mOnCheckedChangeListener.onCheckedChanged(XCheckBox.this, XCheckBoxState.CHECKED_ALL);
                        setXCheckBoxState(XCheckBoxState.CHECKED_ALL);
                    }
                }
            }
        });
    }

    /***
     * 设置选中状态
     *
     * @param xCheckBoxState
     */
    public void setXCheckBoxState(XCheckBoxState xCheckBoxState) {
        this.mXCheckBoxState = xCheckBoxState;
        switch (xCheckBoxState) {
            case CHECKED_ALL:
                this.setImageResource(R.mipmap.check_all);
                break;
            case NO_CHECKED:
                this.setImageResource(R.mipmap.check_no);
                break;
            case CHECKED_PART:
                this.setImageResource(R.mipmap.check_part);
                break;
        }
    }

    public XCheckBoxState getXCheckBoxState(){
        return this.mXCheckBoxState;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(XCheckBox xCheckBox, XCheckBoxState xCheckBoxState);
    }

    public enum XCheckBoxState {
        CHECKED_ALL, NO_CHECKED, CHECKED_PART;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }


}
