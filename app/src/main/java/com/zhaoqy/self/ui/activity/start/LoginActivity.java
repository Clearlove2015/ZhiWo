package com.zhaoqy.self.ui.activity.start;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.litesuits.common.assist.Network;
import com.zhaoqy.self.R;
import com.zhaoqy.self.api.AccountAPI;
import com.zhaoqy.self.app.MsgId;
import com.zhaoqy.self.bean.eventbus.AccountEvent;
import com.zhaoqy.self.ui.activity.MainActivity;
import com.zhaoqy.self.ui.base.BaseEventActivity;
import com.zhaoqy.self.ui.dialog.ProgressDialog;
import com.zhaoqy.self.util.UserUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseEventActivity implements View.OnClickListener {

    @BindView(R.id.login_mobile_layout)
    LinearLayout mMobileLayou;
    @BindView(R.id.login_account_layout)
    LinearLayout mAccountLayout;
    @BindView(R.id.login_edit_mobile)
    EditText mMobileEdit;
    @BindView(R.id.login_edit_vericode)
    EditText mVericodeEdit;
    @BindView(R.id.login_text_vericode)
    TextView mVericodeText;
    @BindView(R.id.login_btn_mobile)
    Button mMobileBtn;
    @BindView(R.id.login_text_account)
    TextView mAccountText;
    @BindView(R.id.login_edit_account)
    EditText mAccountEdit;
    @BindView(R.id.login_edit_pwd)
    EditText mPwdEdit;
    @BindView(R.id.login_btn_account)
    Button mAccountBtn;
    @BindView(R.id.login_text_mobile)
    TextView mMobileText;
    @BindView(R.id.login_lost_pwd)
    TextView mLostPwdText;
    @BindView(R.id.login_text_register)
    TextView mRegister;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorWhite).init();
    }

    @Override
    protected void initToolbox() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListener() {
        setTextChangedListener();
    }

    @OnClick({R.id.login_text_vericode, R.id.login_btn_mobile, R.id.login_text_account,
            R.id.login_btn_account, R.id.login_text_mobile, R.id.login_lost_pwd, R.id.login_text_register})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_text_vericode: {
                /**
                 * 获取验证码
                 */
                getVericode();
                break;
            }
            case R.id.login_btn_mobile: {
                /**
                 * 手机号登录
                 */
                mobileLogin();
                break;
            }
            case R.id.login_text_account: {
                /**
                 * 切换到账号登录
                 */
                mMobileLayou.setVisibility(View.GONE);
                mAccountLayout.setVisibility(View.VISIBLE);
                String accout = mAccountEdit.getText().toString();
                mAccountEdit.setSelection(accout.length());
                mAccountEdit.requestFocus();
                break;
            }
            case R.id.login_btn_account: {
                /**
                 * 账号登录
                 */
                accountLogin();
                break;
            }
            case R.id.login_text_mobile: {
                /**
                 * 切换到手机登录
                 */
                String mobile = mMobileEdit.getText().toString();
                String vercode = mVericodeEdit.getText().toString();
                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(vercode)) {
                    mMobileBtn.setEnabled(true);
                } else {
                    mMobileBtn.setEnabled(false);
                }
                mMobileLayou.setVisibility(View.VISIBLE);
                mAccountLayout.setVisibility(View.GONE);
                break;
            }
            case R.id.login_lost_pwd: {
                /**
                 * 忘记密码
                 */
                Toast.makeText(this, "该功能暂未实现", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.login_text_register: {
                /**
                 * 注册
                 */
                break;
            }
        }
    }

    private void getVericode() {
        String mobile = mMobileEdit.getText().toString();
        if (!AccountAPI.getInstance().isPhoneNum(mobile)) {
            Toast.makeText(this, R.string.account_login_mobile_err, Toast.LENGTH_SHORT).show();
        } else {
            if (!Network.isConnected(this)) {
                Toast.makeText(this, R.string.common_network_abnormal, Toast.LENGTH_SHORT).show();
            } else {
                mCodeTime = GET_CODE_TIMEOUT;
                mVericodeText.setEnabled(false);
                mCodeTimePhone = mobile;
                startCodeTask();
                /**
                 * 获取验证码
                 */
                AccountAPI.getInstance().getVerifyCode(mobile);
            }
        }
    }

    private void mobileLogin() {
        final String mobile = mMobileEdit.getText().toString();
        final String veriCode = mVericodeEdit.getText().toString();
        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(veriCode)) {
            if (!AccountAPI.getInstance().isPhoneNum(mobile)) {
                Toast.makeText(this, R.string.account_login_mobile_err, Toast.LENGTH_SHORT).show();
            } else {
                if (!Network.isConnected(this)) {
                    Toast.makeText(this, R.string.common_network_abnormal, Toast.LENGTH_SHORT).show();
                } else {
                    String str = getResources().getString(
                            R.string.tip_logining);
                    ProgressDialog.showProgress(this, str, null);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            /**
                             * 手机号登录
                             */
                            AccountAPI.getInstance().mobileLogin(mobile, veriCode);
                        }
                    }, 1500);
                }
            }
        }
    }

    private void accountLogin() {
        final String account = mAccountEdit.getText().toString();
        final String pwd = mPwdEdit.getText().toString();
        UserUtil.InputError errorCode = UserUtil.checkInputIsValid(account, pwd);
        switch (errorCode) {
            case eERROR_ACCOUNT_EMPTY: {
                Toast.makeText(this, R.string.account_account_empty, Toast.LENGTH_SHORT).show();
                break;
            }
            case eERROR_PASSWORD_EMPTY: {
                Toast.makeText(this, R.string.account_pasword_empty, Toast.LENGTH_SHORT).show();
                break;
            }
            case eERROR_ACCOUNT_INPUT: {
                Toast.makeText(this, R.string.account_account_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case eERROR_PASSWORD_INPUT: {
                Toast.makeText(this, R.string.account_password_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case eERROR_EMAIL_INPUT: {
                Toast.makeText(this, R.string.account_email_error, Toast.LENGTH_SHORT).show();
                break;
            }
            case eERROR_NONE: {
                if (!Network.isConnected(this)) {
                    Toast.makeText(this, R.string.common_network_abnormal, Toast.LENGTH_SHORT).show();
                } else {
                    String str = getResources().getString(
                            R.string.tip_logining);
                    ProgressDialog.showProgress(this, str, null);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            /**
                             * 账号登录
                             */
                            AccountAPI.getInstance().login(account, pwd);
                        }
                    }, 1500);
                }
                break;
            }
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AccountEvent event) {
        switch (event.msgId) {
            case MsgId.MSGID_GET_LOGIN_VERICODE_SUCCESS: {
                Toast.makeText(this, R.string.account_get_vericode_success, Toast.LENGTH_SHORT).show();
                break;
            }
            case MsgId.MSGID_GET_LOGIN_VERICODE_FAILED: {
                resetGetCodeTimer();
                Toast.makeText(this, R.string.account_get_vericode_failed, Toast.LENGTH_SHORT).show();
                break;
            }
            case MsgId.MSGID_LOGIN_MOBILE_LOGIN_SUCCESS:
            case MsgId.MSGID_LOGIN_ACCOUNT_LOGIN_SUCCESS: {
                cancelCodeTask();

                Toast.makeText(this, R.string.account_login_success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case MsgId.MSGID_LOGIN_MOBILE_LOGIN_FAILED:
            case MsgId.MSGID_LOGIN_ACCOUNT_LOGIN_FAILED: {
                Toast.makeText(this, R.string.account_login_failed, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private static final int GET_CODE_TIMEOUT = 60; // 验证码等待超时
    private int mCodeTime = GET_CODE_TIMEOUT;       // 验证码即时时间
    private String mCodeTimePhone = "";            // 发送验证码对应的电话号码
    private Timer mTimer = new Timer();            // 获取验证码定时器
    private TimerTask mCodeTask = null;            // 获取验证码任务

    private void startCodeTask() {
        cancelCodeTask();
        mCodeTask = new TimerTask() {

            @Override
            public void run() {
                if (null != mCodeHandler) {
                    mCodeHandler.sendEmptyMessage(0);
                }
            }
        };
        mTimer.schedule(mCodeTask, 0, 1000);
    }

    private void cancelCodeTask() {
        if (mCodeTask != null) {
            mCodeTask.cancel();
            mCodeTask = null;
        }
    }

    private void resetGetCodeTimer() {
        cancelCodeTask();
        mCodeTime = GET_CODE_TIMEOUT;
        if (mVericodeText != null) {
            mVericodeText.setEnabled(true);
            @SuppressWarnings("deprecation")
            int color = getResources().getColor(R.color.colorVericodeNormal);
            mVericodeText.setTextColor(color);

            if (mCodeTimePhone.equals(mMobileEdit.getText().toString())) {
                mVericodeText.setText(getResources().getString(R.string.login_resend));
            } else {
                mVericodeText.setText(getResources().getString(R.string.login_get_vericode));
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mCodeHandler = new Handler() {
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    // 验证码倒计时
                    if (mCodeTime <= 0) {
                        resetGetCodeTimer();
                    } else {
                        String vericodeHint = getResources().getString(R.string.login_sended_vericode);
                        String vericode = String.format(vericodeHint, mCodeTime--);
                        @SuppressWarnings("deprecation")
                        int color = getResources().getColor(R.color.colorVericode);
                        mVericodeText.setTextColor(color);
                        mVericodeText.setText(vericode);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void setTextChangedListener() {
        mMobileEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = mMobileEdit.getText().toString();
                String vercode = mVericodeEdit.getText().toString();
                if (!TextUtils.isEmpty(mobile) && mobile.length() == 11) {
                    if (mCodeTime > 0 && mCodeTime < 60 || mCodeTime <= 0) {
                        mVericodeText.setEnabled(false);
                    } else
                        mVericodeText.setEnabled(true);
                } else {
                    mVericodeText.setEnabled(false);
                }

                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(vercode)) {
                    mMobileBtn.setEnabled(true);
                } else {
                    mMobileBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mVericodeEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = mMobileEdit.getText().toString();
                String vercode = mVericodeEdit.getText().toString();
                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(vercode)) {
                    mMobileBtn.setEnabled(true);
                } else {
                    mMobileBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAccountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String account = mAccountEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
                    mAccountBtn.setEnabled(true);
                } else {
                    mAccountBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPwdEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String account = mAccountEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
                    mAccountBtn.setEnabled(true);
                } else {
                    mAccountBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
