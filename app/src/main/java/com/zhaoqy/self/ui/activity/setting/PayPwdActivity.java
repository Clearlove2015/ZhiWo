package com.zhaoqy.self.ui.activity.setting;

import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.Keyboard;
import com.zhaoqy.self.ui.widget.PayEditText;

import butterknife.BindView;

public class PayPwdActivity extends BaseToolboxActivity {

    private static final String[] KEY = new String[] {
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "<<", "0", "完成"
    };

    @BindView(R.id.payedittext)
    PayEditText payedittext;
    @BindView(R.id.keyboard)
    Keyboard keyboard;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_pay_pwd;
    }

    @Override
    protected void initData() {
        /**
         * 设置键盘
         */
        keyboard.setKeyboardKeys(KEY);
        keyboard.setOnClickKeyboardListener(new Keyboard.OnClickKeyboardListener() {

            @Override
            public void onKeyClick(int position, String value) {
                if (position < 11 && position != 9) {
                    payedittext.add(value);
                } else if (position == 9) {
                    payedittext.remove();
                } else if (position == 11) {
                    /**
                     *  当点击完成的时候，也可以通过payEditText.getText()获取密码，
                     *  此时不应该注册OnInputFinishedListener接口
                     */
                    Toast.makeText(PayPwdActivity.this, "您的密码是：" + payedittext.getText(),
                            Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }
        });

        /**
         * 当密码输入完成时的回调
         */
        payedittext.setOnInputFinishedListener(new PayEditText.OnInputFinishedListener() {

            @Override
            public void onInputFinished(String password) {
                Toast.makeText(PayPwdActivity.this, "您的密码是：" + password, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
