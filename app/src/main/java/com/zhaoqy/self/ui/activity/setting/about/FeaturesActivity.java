package com.zhaoqy.self.ui.activity.setting.about;

import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.util.FileUtils;

import butterknife.BindView;

public class FeaturesActivity extends BaseToolboxActivity {

    @BindView(R.id.features_text)
    TextView features;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_features;
    }

    @Override
    protected void initData() {
        String text = FileUtils.getFromAssets(this, "func.txt");
        features.setText(text);
    }
}
