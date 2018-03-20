package com.zhaoqy.self.ui.activity.drawer;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import butterknife.BindView;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends BaseBarActivity {

    public static final String WEB_KEY = "web_key";
    public static final int WEB_GITHUB = 0;
    public static final int WEB_OSCHINA = 1;
    public static final int WEB_CSDN = 2;
    public static final int WEB_JIANSHU = 3;
    public static final int WEB_MAYUN = 4;
    public static final int WEB_BOKEYUAN = 5;
    public static final int WEB_DEVELOPERS = 6;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webview)
    WebView mWebView;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        value = getIntent().getIntExtra(WEB_KEY, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        switch (value) {
            case WEB_GITHUB: {
                title.setText("Github");
                mWebView.loadUrl("https://github.com/zhaoqingyue");
                break;
            }
            case WEB_OSCHINA: {
                title.setText("OsChina");
                mWebView.loadUrl("https://my.oschina.net/u/2377550/blog");
                break;
            }
            case WEB_CSDN: {
                title.setText("CSDN");
                mWebView.loadUrl("http://my.csdn.net/");
                break;
            }
            case WEB_JIANSHU: {
                title.setText("简书");
                mWebView.loadUrl("http://www.jianshu.com/users/b56b76abb6b6/timeline");
                break;
            }
            case WEB_MAYUN: {
                title.setText("码云");
                mWebView.loadUrl("https://git.oschina.net/");
                break;
            }
            case WEB_BOKEYUAN: {
                title.setText("博客园");
                mWebView.loadUrl("http://www.cnblogs.com/zhaoqingyue/");
                break;
            }
            case WEB_DEVELOPERS: {
                title.setText("Android Developers");
                mWebView.loadUrl("https://developer.android.google.cn/about/versions/oreo/index.html");
                break;
            }
        }

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             * Toolbar导航按钮的按键事件
             */
            case android.R.id.home: {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mWebView != null) {
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
