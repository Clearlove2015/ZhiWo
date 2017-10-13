package com.zhaoqy.self.ui.activity.main.tool.step;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.tool.step.adapter.CommonAdapter;
import com.zhaoqy.self.ui.activity.main.tool.step.adapter.CommonViewHolder;
import com.zhaoqy.self.ui.activity.main.tool.step.utils.DbUtils;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.List;

import butterknife.BindView;

public class SetpHistoryActivity extends BaseToolboxActivity {

    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_setp_history;
    }

    @Override
    protected void initData() {
        setEmptyView(listview);
        if(DbUtils.getLiteOrm()==null){
            DbUtils.createDb(this, "ZhiWoStepCount");
        }
        List<StepData> stepDatas =DbUtils.getQueryAll(StepData.class);
        listview.setAdapter(new CommonAdapter<StepData>(this, stepDatas, R.layout.item_step_history) {

            @Override
            protected void convertView(View item, StepData stepData) {
                TextView tv_date= CommonViewHolder.get(item,R.id.tv_date);
                TextView tv_step= CommonViewHolder.get(item,R.id.tv_step);
                tv_date.setText(stepData.getToday());
                tv_step.setText(stepData.getStep()+"步");
            }
        });
    }

    protected void setEmptyView(ListView listView) {
        TextView emptyView = new TextView(this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无数据！");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
    }
}
