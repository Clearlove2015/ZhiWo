package com.zhaoqy.self.ui.activity.main.knowledge.text;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.util.ArrayList;

public class RunTextActivity extends BaseBarActivity {

    private ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_run_text;
    }

    @Override
    protected void initData() {
        titleList.add("你是天上最受宠的一架钢琴");
        titleList.add("我是丑人脸上的鼻涕");
        titleList.add("你发出完美的声音");
        titleList.add("我被默默揩去");
        titleList.add("你冷酷外表下藏着诗情画意");
        titleList.add("我已经够胖还吃东西");
        titleList.add("你踏着七彩祥云离去");
        titleList.add("我被留在这里");
    }
}
