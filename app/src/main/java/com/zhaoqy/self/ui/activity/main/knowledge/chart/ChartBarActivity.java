package com.zhaoqy.self.ui.activity.main.knowledge.chart;

import android.graphics.Color;
import android.graphics.PointF;

import com.idtk.smallchart.chart.BarChart;
import com.idtk.smallchart.data.BarData;
import com.idtk.smallchart.interfaces.iData.IBarData;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.util.DpUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 柱状图
 */
public class ChartBarActivity extends BaseBarActivity {

    @BindView(R.id.barChart)
    BarChart barChart;

    private ArrayList<IBarData> mDataList = new ArrayList<>();
    private BarData mBarData = new BarData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_chart_bar;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 8; i++) {
            mPointArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        mBarData.setValue(mPointArrayList);
        mBarData.setColor(Color.CYAN);
        mBarData.setPaintWidth(DpUtil.px2dip(this, 5));
        mBarData.setTextSize(DpUtil.px2dip(this, 10));
        mDataList.add(mBarData);
        barChart.setDataList(mDataList);
        barChart.setXAxisUnit("X单位");
        barChart.setYAxisUnit("Y单位");
    }
}
