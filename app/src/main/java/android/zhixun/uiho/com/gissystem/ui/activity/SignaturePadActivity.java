package android.zhixun.uiho.com.gissystem.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.zhixun.uiho.com.gissystem.R;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.yibogame.flux.stores.Store;

public class SignaturePadActivity extends BaseActivityWithTitle implements View.OnClickListener {

    private SignaturePad signature_pad;
    private Button btn_clear,btn_save;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_signature_pad;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("签字确认");
        signature_pad = findViewById(R.id.signature_pad);
        btn_clear = findViewById(R.id.btn_clear);
        btn_save = findViewById(R.id.btn_save);
        btn_clear.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        
        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {

            }
        });

    }

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                signature_pad.clear();
                break;
            case R.id.btn_save:
                Bitmap signatureBitmap = signature_pad.getSignatureBitmap();
                break;
        }
    }
}
