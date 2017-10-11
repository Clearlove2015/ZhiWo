package com.zhaoqy.self.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.calculator.CalculatorActivity;
import com.zhaoqy.self.ui.activity.main.flashlight.FlashLightActivity;
import com.zhaoqy.self.ui.activity.main.step.StepMainActivity;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhaoqy on 2017/9/21.
 */

public class ToolFragment extends BaseFragment implements BookAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_tool;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.tool)));
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
                //Intent intent = new Intent(getActivity(), BannerMainActivity.class);
                //startActivity(intent);
                break;
            }
            case 1: {
                //Intent intent = new Intent(getActivity(), MvpActivity.class);
                //startActivity(intent);
                break;
            }
            case 2: {
                Intent intent = new Intent(getActivity(), FlashLightActivity.class);
                startActivity(intent);
                break;
            }
            case 3: {
                /**
                 * 计算器
                 */
                Intent intent = new Intent(getActivity(), CalculatorActivity.class);
                startActivity(intent);
                break;
            }
            case 4: {
                /**
                 * 计步器
                 */
                Intent intent = new Intent(getActivity(), StepMainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getActivity(),"long click " + position + " item", Toast.LENGTH_SHORT).show();
    }
}
