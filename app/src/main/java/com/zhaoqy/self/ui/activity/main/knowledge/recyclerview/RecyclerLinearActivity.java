package com.zhaoqy.self.ui.activity.main.knowledge.recyclerview;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.recycler.RecyclerAdapter;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.LinearDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecyclerLinearActivity extends BaseBarActivity implements View.OnClickListener, RecyclerAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_recycler_linear;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(getData());
        mAdapter = new RecyclerAdapter(mDatas);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDivider(this, LinearLayoutManager.VERTICAL));
    }

    @OnClick({R.id.add, R.id.delete})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add: {
                mAdapter.addNewItem();
                mLayoutManager.scrollToPosition(0);
                break;
            }
            case R.id.delete: {
                mAdapter.deleteItem();
                mLayoutManager.scrollToPosition(0);
                break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,"click item" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this,"long click item" + position , Toast.LENGTH_SHORT).show();
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        String temp = "item";
        for(int i = 0; i < 20; i++) {
            data.add(temp + i);
        }
        return data;
    }
}

