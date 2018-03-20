package com.zhaoqy.self.ui.activity.main.knowledge.chart;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ChartMainActivity extends BaseBarActivity implements BookAdapter.OnItemClickListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_chart_main;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.chart)));
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
                /**
                 * 曲线图
                 */
                Intent intent = new Intent(this, ChartCurveActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                /**
                 * 饼状图
                 */
                Intent intent = new Intent(this, ChartBarActivity.class);
                startActivity(intent);
                break;
            }
            case 2: {
                /**
                 * 折线图
                 */
                Intent intent = new Intent(this, ChartPolylineActivity.class);
                startActivity(intent);
                break;
            }
            case 3: {
                //Intent intent = new Intent(this, SwipeToDeleteActivity.class);
                //startActivity(intent);
                break;
            }
            case 4: {
                /**
                 * 饼状图
                 */
                Intent intent = new Intent(this, ChartPieActivity.class);
                startActivity(intent);
                break;
            }
            case 5: {
                /**
                 * 雷达图
                 */
                Intent intent = new Intent(this, ChartRadarActivity.class);
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
