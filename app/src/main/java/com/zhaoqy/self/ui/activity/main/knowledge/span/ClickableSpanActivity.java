package com.zhaoqy.self.ui.activity.main.knowledge.span;

import android.content.Intent;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import butterknife.BindView;

public class ClickableSpanActivity extends BaseBarActivity {

    @BindView(R.id.text)
    TextView textView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_clickable_span;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent != null) {
            String content = intent.getStringExtra("content");
            textView.setText("这是传过来的内容：" + content);
        }
    }
}
