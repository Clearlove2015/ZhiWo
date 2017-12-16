package com.zhaoqy.self.ui.activity.main.knowledge.chart;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.data.PointShape;
import com.idtk.smallchart.interfaces.iData.ICurveData;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.util.DpUtil;

import java.util.ArrayList;

import butterknife.BindView;

import static com.zhaoqy.self.app.AppContext.getContext;

/**
 * 曲线图
 */
public class ChartCurveActivity extends BaseToolboxActivity {

    @BindView(R.id.curveChart)
    CurveChart curveChart;

    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_chart_curve;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 8; i++) {
            mPointArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        mCurveData.setValue(mPointArrayList);
        mCurveData.setColor(Color.RED);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_fade_red);
        mCurveData.setDrawable(drawable);

        mCurveData.setPointShape(PointShape.SOLIDROUND);
        mCurveData.setPaintWidth(DpUtil.px2dip(this, 3));
        mCurveData.setTextSize(DpUtil.px2dip(this, 10));
        mDataList.add(mCurveData);
        curveChart.setDataList(mDataList);
    }
}
