package android.zhixun.uiho.com.gissystem.ui.fragment;


import android.app.Fragment;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class BaseFragment extends Fragment {

    protected QMUITipDialog mProgressDialog;

    protected void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new QMUITipDialog.Builder(getActivity())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("加载中...")
                    .create();
        }
        mProgressDialog.show();
    }

    protected void dismissLoading() {
        mProgressDialog.dismiss();
    }
}
