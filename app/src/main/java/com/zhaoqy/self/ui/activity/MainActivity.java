package com.zhaoqy.self.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azhon.suspensionfab.FabAttributes;
import com.azhon.suspensionfab.OnFabClickListener;
import com.azhon.suspensionfab.SuspensionFab;
import com.zhaoqy.self.R;
import com.zhaoqy.self.app.AppConst;
import com.zhaoqy.self.ui.activity.drawer.DevelopActivity;
import com.zhaoqy.self.ui.activity.drawer.CloudDiskActivity;
import com.zhaoqy.self.ui.activity.info.UserInfoActivity;
import com.zhaoqy.self.ui.activity.drawer.NewsActivity;
import com.zhaoqy.self.ui.activity.drawer.NoteActivity;
import com.zhaoqy.self.ui.activity.drawer.PlatformActivity;
import com.zhaoqy.self.ui.activity.setting.SettingActivity;
import com.zhaoqy.self.ui.animation.FabAlphaAnimate;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.ui.fragment.BookFragment;
import com.zhaoqy.self.ui.fragment.KnowledgeFragment;
import com.zhaoqy.self.ui.fragment.ToolFragment;
import com.zhaoqy.self.ui.widget.RVPIndicator;
import com.zhaoqy.self.util.SPUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 1. 修改Toolbar导航图标
 * 2. 设置Toolbar的title为空，重新定义title，使title文字居中显示
 * 3. 侧边栏DrawerLayout的使用
 * 4. 沉浸式效果
 * 5. butterknife的使用
 */
public class MainActivity extends BaseBarActivity implements View.OnClickListener, OnFabClickListener {

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.mode_title)
    TextView mode_title;
    @BindView(R.id.mode_name)
    TextView mode_name;
    @BindView(R.id.indicator)
    RVPIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.fab_top)
    SuspensionFab fabTop;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;
    private long mExitTime = 0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initNightMode();
        initTab();
        initFab();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             * Toolbar导航按钮的按键事件
             */
            case android.R.id.home: {
                drawer.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exit();
            //super.onBackPressed();
        }
    }

    @OnClick({R.id.info, R.id.resume, R.id.education, R.id.life,  R.id.develop, R.id.platform, R.id.note,
            R.id.clouddisk, R.id.news, R.id.mode_layout, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info: {
                /**
                 * 信息
                 */
                Intent intent = new Intent(this, UserInfoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.resume: {
                /**
                 * 简历
                 */
                break;
            }
            case R.id.education: {
                /**
                 * 教育
                 */
                break;
            }
            case R.id.life: {
                /**
                 * 生活
                 */
                break;
            }
            case R.id.develop: {
                /**
                 * 开发
                 */
                Intent intent = new Intent(this, DevelopActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.platform: {
                /**
                 * 平台sdk
                 */
                Intent intent = new Intent(this, PlatformActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.note: {
                /**
                 * 笔记
                 */
                Intent intent = new Intent(this, NoteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.clouddisk: {
                /**
                 * 云盘
                 */
                Intent intent = new Intent(this, CloudDiskActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.news: {
                /**
                 * 新闻资讯
                 */
                Intent intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.mode_layout: {
                /**
                 * 夜间-日间模式切换
                 */
                if (SPUtil.getBoolean(AppConst.ISNIGHT, false)) {
                    SPUtil.put(AppConst.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    SPUtil.put(AppConst.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                break;
            }
            case R.id.setting: {
                /**
                 * 设置
                 */
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    /**
     * 退出
     */
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    private void initNightMode() {
        if (SPUtil.getBoolean(AppConst.ISNIGHT, false)) {
            /**
             * 夜间模式
             */
            mode_title.setText("日");
            mode_name.setText("日间");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            /**
             * 日间模式
             */
            mode_title.setText("夜");
            mode_name.setText("夜间");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initTab() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.home_tabs));
        mTabContents = new ArrayList<>();
        mTabContents.add(new KnowledgeFragment());
        mTabContents.add(new BookFragment());
        mTabContents.add(new ToolFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };

        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mIndicator.setViewPager(mViewPager, 0);
    }

    private void initFab() {
        FabAttributes collection = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.like))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();

        FabAttributes email = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#FF9800"))
                .setSrc(getResources().getDrawable(R.mipmap.mail))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();

        FabAttributes news = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#03A9F4"))
                .setSrc(getResources().getDrawable(R.mipmap.news))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(3)
                .build();

        fabTop.addFab(collection, email, news);
        fabTop.setFabClickListener(this);
        fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
        fabTop.setVisibility(View.GONE);
    }

    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        String msg="";
        if (tag.equals(1)) {
            msg="收藏";
        }else if (tag.equals(2)){
            msg="邮件";
        } else if (tag.equals(3)){
            msg="消息";
        }
        Toast.makeText(this, "点击了" + msg ,Toast.LENGTH_SHORT).show();
    }
}
