package com.zhaoqy.self.ui.activity.setting.about;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.CopyrightAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CopyrightActivity extends BaseToolboxActivity implements CopyrightAdapter.OnItemClickListener  {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CopyrightAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_copyright;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.copyright)));
        mAdapter = new CopyrightAdapter(mDatas);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, GithubActivity.class);
        intent.putExtra(GithubActivity.GITHUB_KEY, position);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
