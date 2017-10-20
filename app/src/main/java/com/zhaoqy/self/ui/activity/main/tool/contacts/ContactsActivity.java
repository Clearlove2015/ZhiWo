package com.zhaoqy.self.ui.activity.main.tool.contacts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.RVPIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ContactsActivity extends BaseToolboxActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.indicator)
    RVPIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private List<Fragment> mContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initData() {
        int type = getIntent().getIntExtra("contact_type", 0);
        mDatas = Arrays.asList(getResources().getStringArray(R.array.contacts));
        mContents = new ArrayList<>();
        mContents.add(new CallLogFragment());
        mContents.add(new ContactFragment());
        mContents.add(new MessageFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }
        };

        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mIndicator.setViewPager(mViewPager, type);
    }
}
