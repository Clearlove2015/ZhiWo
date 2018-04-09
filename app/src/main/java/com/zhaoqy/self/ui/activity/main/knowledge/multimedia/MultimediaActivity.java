package com.zhaoqy.self.ui.activity.main.knowledge.multimedia;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.knowledge.multimedia.record.RecordActivity;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MultimediaActivity extends BaseBarActivity implements BookAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_multimedia;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.multimedia)));
        mAdapter = new BookAdapter(mDatas);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
    }
}
