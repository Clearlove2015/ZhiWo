package com.zhaoqy.self.ui.activity.main.knowledge.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhaoqy.self.R;
import com.zhaoqy.self.mvp.model.Girl;
import com.zhaoqy.self.mvp.presenter.GirlPresenter;
import com.zhaoqy.self.mvp.view.GirlView;
import com.zhaoqy.self.ui.adapter.GirlAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.List;

import butterknife.BindView;

public class MvpActivity extends BaseToolboxActivity implements GirlView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.mvp)
    LinearLayout mvp;
    @BindView(R.id.recyclerview)
    EasyRecyclerView recyclerview;

    private GirlAdapter adapter;
    private int page = 1;
    private GirlPresenter mPresenter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initData() {
        mPresenter = new GirlPresenter();
        mPresenter.attachView(this);
        mPresenter.getGirlPic(page);

        recyclerview.setRefreshing(true);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        adapter = new GirlAdapter(this);
        recyclerview.setAdapter(adapter);
        /**
         * 下拉刷新
         */
        recyclerview.setRefreshListener(this);
        /**
         * 上拉加载更多
         */
        adapter.setMore(R.layout.layout_load_more, new RecyclerArrayAdapter.OnMoreListener() {

            @Override
            public void onMoreShow() {
                page++;
                mPresenter.getGirlPic(page);
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void refresh(List<Girl.ResultsBean> data) {
        recyclerview.setRefreshing(false);
        adapter.addAll(data);
    }

    @Override
    public void loadMore(List<Girl.ResultsBean> data) {
        adapter.addAll(data);
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showError(String msg) {
        Snackbar.make(mvp, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        recyclerview.setRefreshing(true);
        adapter.clear();
        page = 1;
        mPresenter.getGirlPic(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PicUrl", adapter.getAllData().get(position).getUrl());
        bundle.putString("PicId", adapter.getAllData().get(position).get_id());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
