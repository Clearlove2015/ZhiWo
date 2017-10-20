package com.zhaoqy.self.ui.activity.main.knowledge.recyclerview;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.recycler.StaggeredAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecyclerStaggerActivity extends BaseToolboxActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    StaggeredAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_recycler_staggered;
    }

    @Override
    protected void initData() {
        mAdapter = new StaggeredAdapter(getData());
        mLayoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        String temp = "item";
        for(int i = 0; i < 50; i++) {
            data.add(temp + i);
        }
        return data;
    }
}
