package com.zhaoqy.self.ui.activity.drawer;

import android.content.Intent;
import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.OnClick;

/**
 * 遇到的问题：
 * Toolbar的导航返回按钮是黑色的，设置Toolbar的父布局的主题为
 * android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
 * 设置完后，Toolbar的导航返回按钮变成白色
 */
public class DevelopActivity extends BaseToolboxActivity implements View.OnClickListener{

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_develop;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.github, R.id.oschina, R.id.csdn, R.id.jiansu, R.id.mayun, R.id.bokeyuan, R.id.developers})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.github: {
                /**
                 * Github
                 */
                /**
                 * github
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_GITHUB);
                startActivity(intent);
                break;
            }
            case R.id.oschina: {
                /**
                 * OsChina
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_OSCHINA);
                startActivity(intent);
                break;
            }
            case R.id.csdn: {
                /**
                 * CSDN
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_CSDN);
                startActivity(intent);
                break;
            }
            case R.id.jiansu: {
                /**
                 * 简书
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_JIANSHU);
                startActivity(intent);
                break;
            }
            case R.id.mayun: {
                /**
                 * 码云
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_MAYUN);
                startActivity(intent);
                break;
            }
            case R.id.bokeyuan: {
                /**
                 * 博客园
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_BOKEYUAN);
                startActivity(intent);
                break;
            }
            case R.id.developers: {
                /**
                 * Android Developers
                 */
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_KEY, WebViewActivity.WEB_DEVELOPERS);
                startActivity(intent);
                break;
            }
        }
    }
}
