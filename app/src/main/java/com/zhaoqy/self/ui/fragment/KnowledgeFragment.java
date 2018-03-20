package com.zhaoqy.self.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.knowledge.album.AlbumActivity;
import com.zhaoqy.self.ui.activity.main.knowledge.banner.BannerMainActivity;
import com.zhaoqy.self.ui.activity.main.knowledge.canvas.CanvasActivity;
import com.zhaoqy.self.ui.activity.main.knowledge.chart.ChartMainActivity;
import com.zhaoqy.self.ui.activity.main.knowledge.mvp.MvpActivity;
import com.zhaoqy.self.ui.activity.main.knowledge.recyclerview.RecyclerViewActivity;
import com.zhaoqy.self.ui.activity.main.knowledge.span.SpanActivity;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhaoqy on 2017/9/21.
 */

public class KnowledgeFragment extends BaseFragment implements BookAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.knowledge)));
        mAdapter = new BookAdapter(mDatas);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new LinearDivider(getActivity(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0: {
                /**
                 * Banner图片轮播
                 */
                Intent intent = new Intent(getActivity(), BannerMainActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                /**
                 * MVP模式
                 */
                Intent intent = new Intent(getActivity(), MvpActivity.class);
                startActivity(intent);
                break;
            }
            case 2: {
                /**
                 * 相册选择器
                 */
                //Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                startActivity(intent);
                break;
            }
            case 3: {
                /**
                 * RecyclerView
                 */
                Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
                startActivity(intent);
                break;
            }
            case 4: {
                /**
                 * Chart图表
                 */
                Intent intent = new Intent(getActivity(), ChartMainActivity.class);
                startActivity(intent);
                break;
            }
            case 5: {
                /**
                 * Canvas
                 */
                Intent intent = new Intent(getActivity(), CanvasActivity.class);
                startActivity(intent);
                break;
            }
            case 6: {
                /**
                 * SpannableString
                 */
                Intent intent = new Intent(getActivity(), SpanActivity.class);
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
