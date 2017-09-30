package com.zhaoqy.self.ui.activity.info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.othershe.nicedialog.NiceDialog;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditActivity extends BaseToolboxActivity implements View.OnClickListener{

    public static final int REQUEST_NICKNAME = 1;
    public static final int REQUEST_SIGNATURE = 2;
    public static final String EDIT_TITLE = "edit_title";
    public static final String EDIT_KEY = "edit_key";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.btn)
    Button btn;

    String title;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getIntent().getStringExtra(EDIT_TITLE);
        value = getIntent().getStringExtra(EDIT_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initData() {
        mTitle.setText(title);
        edit.setText(value);
        edit.setSelection(value.length());
        edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit.getText().toString().trim().length() > 0) {
                    btn.setEnabled(true);
                } else {
                    btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn: {
                String text = edit.getText().toString();
                if (!TextUtils.isEmpty(text) && !text.equals(value)) {
                    NiceDialog.init()
                            .setLayoutId(R.layout.dialog_loading)
                            .setWidth(200)
                            .setHeight(80)
                            .setDimAmount(0)
                            .show(getSupportFragmentManager());
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
                break;
            }
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    Toast.makeText(EditActivity.this, "修改成功",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    String text = edit.getText().toString();
                    intent.putExtra(EDIT_KEY, text);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    };
}
