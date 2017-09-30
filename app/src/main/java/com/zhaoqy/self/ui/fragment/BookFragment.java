package com.zhaoqy.self.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.BookActivity;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhaoqy on 2017/9/21.
 */
public class BookFragment extends BaseFragment{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_book;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.book_classify)));
        //mDatas.add("添加");
        mAdapter = new BookAdapter(mDatas);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new LinearDivider(getActivity(), LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                /*if (position == mDatas.size()-1) {
                    mAdapter.addNewItem();
                    mLayoutManager.scrollToPosition(0);
                }*/

                String title = mDatas.get(position);
                Intent intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra(BookActivity.BOOK_KEY, title);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
