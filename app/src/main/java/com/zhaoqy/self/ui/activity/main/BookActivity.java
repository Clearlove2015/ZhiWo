package com.zhaoqy.self.ui.activity.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.LinearDivider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class BookActivity extends BaseBarActivity {

    public static final String BOOK_KEY = "book_key";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.swipefefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getIntent().getStringExtra(BOOK_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_book;
    }

    @Override
    protected void initData() {
        mTitle.setText(title);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(getData(title));
        mAdapter = new BookAdapter(mDatas);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDivider(this, LinearLayoutManager.VERTICAL));

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                fresh();
            }
        });
    }

    private List<String> getData(String title) {
        List<String> datas = new ArrayList<>();
        if (title.equals("编程")) {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_program)));
        } else if (title.equals("互联网")) {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_internet)));
        } else if (title.equals("心理学")) {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_psychology)));
        } else if (title.equals("历史")) {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_history)));
        } else if (title.equals("小说")) {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_fiction)));
        } else if (title.equals("生活")) {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_life)));
        } else {
            datas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_classify)));
        }
        return datas;
    }

    public void fresh() {
        swipeRefreshLayout.setRefreshing(true);
        handler.sendEmptyMessageDelayed(0, 4000);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(BookActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
