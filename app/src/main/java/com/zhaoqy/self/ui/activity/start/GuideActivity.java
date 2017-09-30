package com.zhaoqy.self.ui.activity.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.GuidePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.guide_pager)
    ViewPager mViewPager;
    @BindView(R.id.guide_jump)
    TextView mJump;
    Button mEnter;

    Unbinder unbinder;
    GuidePagerAdapter mAdapter;
    List<View> mViews;
    static final int[] pics = {R.layout.layout_guid_view0,
            R.layout.layout_guid_view1, R.layout.layout_guid_view2,
            R.layout.layout_guid_view3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        unbinder = ButterKnife.bind(this);
        initViews();
    }

    protected void initViews() {
        mViews = new ArrayList<View>();
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
           if (i == pics.length - 1) {
                mEnter = (Button) view.findViewById(R.id.guide_enter);
                mEnter.setTag("enter");
                mEnter.setOnClickListener(this);
            }
            mViews.add(view);
        }

        mAdapter = new GuidePagerAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new PageChangeListener());
    }

    private class PageChangeListener implements OnPageChangeListener {
        /**
         * 当滑动状态改变时调用
         */
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        /**
         * 当前页面被滑动时调用
         */
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置
        }

        /**
         * 当新的页面被选中时调用
         */
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mJump.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mJump.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mJump.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mJump.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.guide_jump})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_jump:
            case R.id.guide_enter: {
                /**
                 * 开始体验
                 */
                //Intent intent = new Intent(this, MainActivity.class);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
