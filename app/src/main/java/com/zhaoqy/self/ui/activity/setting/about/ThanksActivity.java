package com.zhaoqy.self.ui.activity.setting.about;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.ThankAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.LinearDivider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ThanksActivity extends BaseToolboxActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ThankAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_thanks;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.thanks)));
        mAdapter = new ThankAdapter(mDatas);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDivider(this, LinearLayoutManager.VERTICAL));
    }
}
