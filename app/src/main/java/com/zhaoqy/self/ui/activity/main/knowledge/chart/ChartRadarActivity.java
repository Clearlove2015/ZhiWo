package com.zhaoqy.self.ui.activity.main.knowledge.chart;

import android.graphics.Color;

import com.idtk.smallchart.chart.RadarChart;
import com.idtk.smallchart.data.RadarData;
import com.idtk.smallchart.interfaces.iData.IRadarData;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.util.DpUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 雷达图
 */
public class ChartRadarActivity extends BaseBarActivity {

    @BindView(R.id.radarChart)
    RadarChart radarChart;

    private RadarData mRadarData = new RadarData();
    private RadarData mRadarData2 = new RadarData();
    private ArrayList<IRadarData> radarDataList = new ArrayList<>();
    private ArrayList<Float> radarValue = new ArrayList<>();
    private ArrayList<Float> radarValue2 = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_chart_radar;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 8; i++) {
            radarValue.add(points[i][1]);
            radarValue2.add(points2[i][1]);
        }
        mRadarData.setValue(radarValue);
        mRadarData.setColor(Color.MAGENTA);
        mRadarData.setPaintWidth(DpUtil.px2dip(this, 3));
        mRadarData2.setValue(radarValue2);
        mRadarData2.setColor(Color.CYAN);
        mRadarData2.setPaintWidth(DpUtil.px2dip(this, 3));
        radarDataList.add(mRadarData);
        radarDataList.add(mRadarData2);

        radarChart.setDataList(radarDataList);
        radarChart.setTypes(new String[] {"Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H"});
    }
}
