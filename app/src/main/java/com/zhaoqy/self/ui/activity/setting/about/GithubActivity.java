package com.zhaoqy.self.ui.activity.setting.about;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseActivity;

import butterknife.BindView;

import static android.view.KeyEvent.KEYCODE_BACK;

public class GithubActivity extends BaseActivity {

    public static final String GITHUB_KEY = "github_key";

    @BindView(R.id.webview)
    WebView mWebView;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        value = getIntent().getIntExtra(GITHUB_KEY, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_github;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarColor(
                R.color.colorGithub).init();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initToolbox() {
        switch (value) {
            case 0: {
                /**
                 * butterknife 依赖注入
                 */
                mWebView.loadUrl("https://github.com/JakeWharton/butterknife");
                break;
            }
            case 1: {
                /**
                 * permission 动态申请权限
                 */
                mWebView.loadUrl("https://github.com/yanzhenjie/AndPermission");
                break;
            }
            case 2: {
                /**
                 * barlibrary 沉浸式管理
                 */
                mWebView.loadUrl("https://github.com/gyf-dev/ImmersionBar");
                break;
            }
            case 3: {
                /**
                 * circleimageview 圆形imageView
                 */
                mWebView.loadUrl("https://github.com/hdodenhof/CircleImageView");
                break;
            }
            case 4: {
                /**
                 * photoview 缩放图片PhotoView
                 */
                mWebView.loadUrl("https://github.com/chrisbanes/PhotoView");
                break;
            }
            case 5: {
                /**
                 * Glide加载
                 */
                mWebView.loadUrl("https://github.com/bumptech/glide");
                break;
            }
            case 6: {
                /**
                 * Picasso加载
                 */
                mWebView.loadUrl("https://github.com/square/picasso");
                break;
            }
            case 7: {
                /**
                 * Fresco加载
                 */
                mWebView.loadUrl(" https://github.com/facebook/fresco");
                break;
            }
            case 8: {
                /**
                 * GifView图片的显示
                 */
                mWebView.loadUrl("https://github.com/Cutta/GifView");
                break;
            }
            case 9: {
                /**
                 * Blurry图片模糊
                 */
                mWebView.loadUrl("https://github.com/wasabeef/Blurry");
                break;
            }
            case 10: {
                /**
                 * Banner图片轮询控件
                 */
                mWebView.loadUrl("https://github.com/youth5201314/banner");
                break;
            }
            case 11: {
                /**
                 * NiceDialog 通用dialog
                 */
                mWebView.loadUrl("https://github.com/Othershe/NiceDialog");
                break;
            }
            case 12: {
                /**
                 * android-percent-support-extend 比例布局
                 */
                mWebView.loadUrl("https://github.com/hongyangAndroid/android-percent-support-extend");
                break;
            }
            case 13: {
                /**
                 * EventBus
                 */
                mWebView.loadUrl("https://github.com/greenrobot/EventBus");
                break;
            }
            case 14: {
                /**
                 * bga-qrcodecore 二维码相关
                 */
                mWebView.loadUrl("https://github.com/bingoogolapple/BGAQRCode-Android");
                break;
            }
            case 15: {
                /**
                 * android-common-utils 通用工具
                 */
                mWebView.loadUrl("https://github.com/luffykou/android-common");
                break;
            }
            case 16: {
                /**
                 * MaterialRatingBar 评分
                 */
                mWebView.loadUrl("https://github.com/DreaminginCodeZH/MaterialRatingBar");
                break;
            }
            case 17: {
                /**
                 * suspension-fab 悬浮按钮
                 */
                mWebView.loadUrl("https://github.com/azhon/SuspensionFAB");
                break;
            }
            case 18: {
                /**
                 * recovery 自动处理程序在运行时的Crash
                 */
                mWebView.loadUrl("https://github.com/Sunzxyong/Recovery");
                break;
            }
            case 19: {
                /**
                 * EasyRecyclerView
                 */
                mWebView.loadUrl("https://github.com/Jude95/EasyRecyclerView");
            }
            case 20: {
                /**
                 * leakcanary 内存泄漏检测库
                 */
                mWebView.loadUrl("https://github.com/square/leakcanary");
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
