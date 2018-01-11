package android.zhixun.uiho.com.gissystem.interfaces;

import android.widget.CompoundButton;

/**
 * Created by parcool on 2016/7/20.
 */

public interface OnExpandableItemCheckedListener {
    //    CompoundButton buttonView, boolean isChecked
    void onItemChecked(CompoundButton buttonView, boolean isChecked, int parentPosition, int childPosition);
}
