package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;

/**
 * Created by simple on 2018/1/4.
 */

public class SimpleAlertDialog extends AppCompatDialog {

    private View mRootView;
    private TextView mTvTitle, mTvMessage, mTvOk, mTvCancel;
    private EditText mEtInput;

    public SimpleAlertDialog(Context context) {
        super(context);

        mRootView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_symbol, null);

        setContentView(mRootView);
        mTvTitle = mRootView.findViewById(R.id.tv_dialogTitle);
        mTvMessage = mRootView.findViewById(R.id.tv_dialogMessage);
        mTvOk = mRootView.findViewById(R.id.tv_dialogOk);
        mTvCancel = mRootView.findViewById(R.id.tv_dialogCancel);
        mEtInput = mRootView.findViewById(R.id.et_dialogInput);
    }

    public SimpleAlertDialog title(CharSequence title) {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(title);
        return this;
    }

    public SimpleAlertDialog message(CharSequence message) {
        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(message);
        return this;
    }

    public SimpleAlertDialog setOkOnClickListener(CharSequence text, SimpleAlertDialog.OnClickListener onClickListener) {
        mTvOk.setVisibility(View.VISIBLE);
        mTvOk.setText(text);
        mTvOk.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(SimpleAlertDialog.this, v);
            } else {
                dismiss();
            }
        });
        return this;
    }

    public SimpleAlertDialog setOkOnClickListener(@StringRes int resId, SimpleAlertDialog.OnClickListener onClickListener) {
        this.setOkOnClickListener(getContext().getResources().getString(resId), onClickListener);
        return this;
    }

    public SimpleAlertDialog setCancelOnClickListener(CharSequence text,
                                                      SimpleAlertDialog.OnClickListener onClickListener) {
        mTvCancel.setVisibility(View.VISIBLE);
        mTvCancel.setText(text);
        mTvCancel.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(SimpleAlertDialog.this, v);
            } else {
                dismiss();
            }
        });
        return this;
    }

    public SimpleAlertDialog setCancelOnClickListener(@StringRes int resId,
                                                      SimpleAlertDialog.OnClickListener onClickListener) {
        this.setCancelOnClickListener(getContext().getResources().getString(resId), onClickListener);
        return this;
    }

    public SimpleAlertDialog visibleEditText() {
        mEtInput.setVisibility(View.VISIBLE);
        return this;
    }

    public SimpleAlertDialog setInputType(){
        return this;
    }

    public EditText getEditText() {
        return mEtInput;
    }

    public SimpleAlertDialog alert() {
        show();
        return this;
    }

    public interface OnClickListener {
        void onClick(SimpleAlertDialog dialog, View view);
    }
}
