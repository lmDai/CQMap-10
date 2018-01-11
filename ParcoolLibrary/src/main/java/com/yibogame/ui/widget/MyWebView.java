package com.yibogame.ui.widget;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yibogame.R;
import com.yibogame.util.ToastUtil;

import java.io.File;

/**
 * Created by parcool on 2016/8/30.
 */

public class MyWebView extends WebView {

    protected WebSettings webSettings;

    interface IMyWebView {
        void onReceivedTitle(String title);

        void onProgress(int progress);
    }

    public IMyWebView iMyWebView;

    public void setiMyWebView(IMyWebView iMyWebView) {
        this.iMyWebView = iMyWebView;
    }

    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    private void init() {
        setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        setFocusable(true);
        setFocusableInTouchMode(true);
//        requestFocus(View.FOCUS_DOWN);

        webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //开启DomStorage缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        ////设置支持加载图片
        webSettings.setBlockNetworkImage(false);
        //设置缓存模式为默认
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true);


        this.setWebViewClient(mWebViewClientBase);
        this.setWebChromeClient(mWebChromeClientBase);
        this.requestFocusFromTouch();
        this.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            return super.shouldOverrideUrlLoading(view, url);
//            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            // TODO Auto-generated method stub
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    private WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase();

    private class WebChromeClientBase extends WebChromeClient {

        boolean isResetProgressBar = true;

        @Override
        public void onProgressChanged(WebView view, final int newProgress) {
            if (newProgress >= 100 && isResetProgressBar) {
                isResetProgressBar = false;
                MyWebView.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isResetProgressBar = true;
                        if (iMyWebView != null) {
                            iMyWebView.onProgress(newProgress);
                        }
                    }
                }, 500);
            } else {
                if (iMyWebView != null) {
                    iMyWebView.onProgress(newProgress);
                }
            }

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // TODO Auto-generated method stub
            super.onReceivedTitle(view, title);
            if (iMyWebView != null) {
                iMyWebView.onReceivedTitle(title);
            }
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                                           boolean precomposed) {
            // TODO Auto-generated method stub
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            // TODO Auto-generated method stub
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);

        }

    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // TODO: handle exception
                e.printStackTrace();
                ToastUtil.showLong(e.getMessage() + " 无法下载：" + url);
            }
        }

    }

}
