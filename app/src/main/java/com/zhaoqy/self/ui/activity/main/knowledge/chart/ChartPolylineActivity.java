package com.zhaoqy.self.ui.activity.main.knowledge.chart;

import android.graphics.Color;
import android.graphics.PointF;

import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.interfaces.iData.ILineData;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.util.DpUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 折线图
 */
public class ChartPolylineActivity extends BaseBarActivity {

    @BindView(R.id.lineChart)
    LineChart lineChart;

    private LineData mLineData = new LineData();
    private ArrayList<ILineData> mDataList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_chart_polyline;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 8; i++) {
            mPointArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        mLineData.setValue(mPointArrayList);
        mLineData.setColor(Color.MAGENTA);
        mLineData.setPaintWidth(DpUtil.px2dip(this, 3));
        mLineData.setTextSize(DpUtil.px2dip(this, 10));
        mDataList.add(mLineData);
        lineChart.setDataList(mDataList);
    }
}
