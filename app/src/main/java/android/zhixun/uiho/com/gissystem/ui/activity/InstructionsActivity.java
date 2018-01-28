package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.zhixun.uiho.com.gissystem.R;

import com.yibogame.flux.stores.Store;

/**
 * Created by simple on 2017/12/7.
 */

public class InstructionsActivity extends BaseActivityWithTitle {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_instructions;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("使用说明");
        mWebView = findViewById(R.id.webview);
        mProgressBar = findViewById(R.id.progressBar);

        mWebView.loadUrl("http://www.map023.cn:8080/moveImg/index.html");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
//        mWebView.setWebViewClient(new WebViewClient(){
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                mProgressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mProgressBar.setVisibility(View.GONE);
//            }
//        });
    }


    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }
}
