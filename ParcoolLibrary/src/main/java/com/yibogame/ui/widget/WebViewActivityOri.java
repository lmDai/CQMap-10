package com.yibogame.ui.widget;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yibogame.R;
import com.yibogame.ui.activity.OriBaseToolbarActivity;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by parcool on 2016/8/29.
 */

public abstract class WebViewActivityOri extends OriBaseToolbarActivity {

    protected MyWebView webView;
    protected WebSettings webSettings;
    private ProgressBar progressBar;

    protected abstract void loadUrl(MyWebView webView);

    protected abstract void onShare(MyWebView webView);

    protected abstract void openWithBrowser(MyWebView webView);


    @Override
    protected void onToolBarCreateView(CoordinatorLayout coordinatorLayout, Bundle savedInstanceState) {
//        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        View childView = getLayoutInflater().inflate(R.layout.layout_webview, null, false);
        childView.setLayoutParams(layoutParams);
        coordinatorLayout.addView(childView);

        setTitleStr("");
        init();
        loadUrl(webView);
        webView.onResume();
//        webView.requestFocus();
    }

    private void init() {
        webView = (MyWebView) findViewById(R.id.webview);
        webSettings = webView.getSettings();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        webView.setiMyWebView(new MyWebView.IMyWebView() {
            @Override
            public void onReceivedTitle(String title) {
                setTitleStrSmall(title);
            }

            @Override
            public void onProgress(int progress) {
                if (progress >= 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(progress);
            }

        });
    }


    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.onPause(); // 暂停网页中正在播放的视频
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.loadUrl("about:blank");
        finish();
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*  利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
         *  给菜单设置图标时才可见
         */
        setIconEnable(menu, true);
//        setMenuItemTextColor(R.color.colorMenuItemColor);
        getMenuInflater().inflate(R.menu.menu_webview_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
    private void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("android.support.v7.view.menu.MenuBuilder");//如果不是support那就是这个包名：com.android.internal.view.menu.MenuBuilder
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMenuItemTextColor(final int color) {
        LayoutInflater layoutInflater = getLayoutInflater();
        final LayoutInflater.Factory existingFactory = layoutInflater.getFactory();
// use introspection to allow a new Factory to be set
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
            getLayoutInflater().setFactory(new LayoutInflater.Factory() {
                @Override
                public View onCreateView(String name, final Context context, AttributeSet attrs) {
                    View view = null;
                    if (existingFactory != null) {
                        view = existingFactory.onCreateView(name, context, attrs);
                    }
                    // if a factory was already set, we use the returned view
//                    if (existingFactory != null) {
//                        view = existingFactory.onCreateView(name, context, attrs);
//                    }
//                    // do whatever you want with the null or non-null view
//                    // such as expanding 'IconMenuItemView' and changing its style
//                    // or anything else...
//                    // and return the view
//                    System.out.println((view instanceof TextView));
//                    if (view instanceof TextView) {
//                        ((TextView) view).setTextColor(getResources().getColor(color));
//                    }
//                    return view;
                    LogUtil.d("name is:" + name);
                    if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                            || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
                        try {
//                            LayoutInflater f = getLayoutInflater();
//                            final View view = f.createView(name, null, attrs);

                            System.out.println((view instanceof TextView));
                            if (view instanceof TextView) {
                                ((TextView) view).setTextColor(getResources().getColor(color));
                                ToastUtil.showShort("set color!");
                            }
                            return view;
                        } catch (InflateException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            });
        } catch (NoSuchFieldException e) {
            // ...
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // ...
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // ...
            e.printStackTrace();
        }


        // Inflate the menu; this adds items to the action bar if it is present.

//        LayoutInflater inflater = LayoutInflater.from(this);
////        if (inflater.getFactory() != null) {
////            inflater = inflater.cloneInContext(this);
////        }
//        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
//                        || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
//                    try {
//                        LayoutInflater f = getLayoutInflater();
//                        final View view = f.createView(name, null, attrs);
//                        System.out.println((view instanceof TextView));
//                        if (view instanceof TextView) {
//                            ((TextView) view).setTextColor(getResources().getColor(color));
//                        }
//                        return view;
//                    } catch (InflateException e) {
//                        e.printStackTrace();
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return null;
//            }
//        });
//        inflater.setFactory(new LayoutInflater.Factory() {
//
//            @Override
//            public View onCreateView(String name, Context context, AttributeSet attrs) {
//                System.out.println(name);
//                if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
//                        || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
//                    try {
//                        LayoutInflater f = getLayoutInflater();
//                        final View view = f.createView(name, null, attrs);
//                        System.out.println((view instanceof TextView));
//                        if (view instanceof TextView) {
//                            ((TextView) view).setTextColor(getResources().getColor(color));
//                        }
//                        return view;
//                    } catch (InflateException e) {
//                        e.printStackTrace();
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return null;
//            }
//
//        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Resource IDs cannot be used in a switch statement in Android library modules
//        switch (id) {
//            case R.id.refresh:
//                break;
//        }
        if (id == R.id.refresh) {
            webView.reload();
        } else if (id == R.id.share) {
            onShare(webView);
        } else if (id == R.id.open_with_browser) {
            openWithBrowser(webView);
        }
        return super.onOptionsItemSelected(item);
    }


}
