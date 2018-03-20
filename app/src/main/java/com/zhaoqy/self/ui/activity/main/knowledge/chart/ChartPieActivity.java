package com.zhaoqy.self.ui.activity.main.knowledge.chart;

import android.graphics.Color;

import com.idtk.smallchart.chart.PieChart;
import com.idtk.smallchart.data.PieData;
import com.idtk.smallchart.interfaces.iData.IPieData;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.util.DpUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 饼状图
 */
public class ChartPieActivity extends BaseBarActivity {

    @BindView(R.id.pieChart)
    PieChart pieChart;

    private ArrayList<IPieData> mPieDataList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_chart_pie;
    }

    @Override
    protected void initData() {
        for (int i=0; i<9; i++){
            PieData pieData = new PieData();
            pieData.setName("区域"+i);
            pieData.setValue((float)i+1);
            pieData.setColor(mColors[i]);
            mPieDataList.add(pieData);
        }

        pieChart.setDataList(mPieDataList);
        pieChart.setAxisColor(Color.WHITE);
        pieChart.setAxisTextSize(DpUtil.px2dip(this, 20));
    }
}
