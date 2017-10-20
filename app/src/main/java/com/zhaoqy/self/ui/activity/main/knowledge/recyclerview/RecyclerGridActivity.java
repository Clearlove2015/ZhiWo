package com.zhaoqy.self.ui.activity.main.knowledge.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.recycler.RecyclerAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.GridDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecyclerGridActivity extends BaseToolboxActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_recycler_grid;
    }

    @Override
    protected void initData() {
        mLayoutManager = new GridLayoutManager(this, 4, OrientationHelper.VERTICAL, false);
        mAdapter = new RecyclerAdapter(getData());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new GridDivider(this));
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        String temp = "item";
        for(int i = 0; i < 40; i++) {
            data.add(temp + i);
        }
        return data;
    }
}
