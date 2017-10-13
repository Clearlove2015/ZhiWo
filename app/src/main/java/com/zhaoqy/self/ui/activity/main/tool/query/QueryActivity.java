package com.zhaoqy.self.ui.activity.main.tool.query;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.api.Network;
import com.zhaoqy.self.bean.query.QueryIDCard;
import com.zhaoqy.self.bean.query.QueryTel;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.dialog.ProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QueryActivity extends BaseToolboxActivity implements View.OnClickListener {

    public static final String QUERY_IDCARD_KEY = "220f329adbf071c81b4b3011e0439cad";
    public static final String QUERY_TEL_KEY = "576f995fdbc2c0e9db5fa785da34efd6";
    public static final String QUERY_STYLE="style";
    public static final int QUERY_TEL = 1;
    public static final int QUERY_IDCARD = 2;
    public static final String[] sQueryStyleName = {"手机号归属地","身份证号查询"};

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.result)
    TextView mResult;
    @BindView(R.id.inputtext)
    EditText mInputText;
    @BindView(R.id.tip)
    TextView mTip;

    public int mQueryStyle;
    private Subscription mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mQueryStyle = intent.getIntExtra(QUERY_STYLE, 0);
        if (mQueryStyle == 0) {
            Toast.makeText(this, "页面入口有误", Toast.LENGTH_SHORT).show();
            finish();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_query;
    }

    @Override
    protected void initData() {
        mTitle.setText(sQueryStyleName[mQueryStyle-1]);
        initEditText();
    }

    Observer<Object> mObserver = new Observer<Object>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Object object) {
            try{
                switch (mQueryStyle) {
                    case QUERY_IDCARD: {
                        QueryIDCard queryIDCard = (QueryIDCard) object;
                        showResult(queryIDCard.getResult().toString());
                        break;
                    }
                    case QUERY_TEL: {
                        QueryTel queryTel = (QueryTel) object;
                        showResult(queryTel.getResult().toString());
                        break;
                    }
                }
            }catch (Exception exception){
                showResult(null);
            }
        }
    };

    private void showResult(String s) {
        if (ProgressDialog.isShowProgress()) {
            ProgressDialog.cancelProgress();
        }

        if (TextUtils.isEmpty(s)) {
            mResult.setText("查询结果有误，请检查输入号码是否有误!");
        }else {
            mResult.setText(s);
        }
    }

    private void initEditText() {
        switch(mQueryStyle){
            case QUERY_IDCARD: {
                mInputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                mInputText.setHint("请输入要查询的身份证号码");
                break;
            }
            case QUERY_TEL: {
                mInputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                mInputText.setHint("请输入要查询的手机号码");
                break;
            }
        }
    }

    @OnClick({R.id.query})
    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(mInputText.getText())) {
            mTip.setVisibility(View.VISIBLE);
        }else {
            if (!com.litesuits.common.assist.Network.isConnected(this)) {
                Toast.makeText(this, R.string.common_network_abnormal, Toast.LENGTH_SHORT).show();
            } else {
                String str = getResources().getString(
                        R.string.tip_querying);
                ProgressDialog.showProgress(this, str, null);
                mTip.setVisibility(View.GONE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        /**
                         * 开始查询
                         */
                        requestData();
                    }
                }, 500);
            }
        }
    }

    private void requestData() {
        unsubscribe();
        switch(mQueryStyle){
            case QUERY_IDCARD: {
                mSubscription = Network.getQueryIDCardApi()
                        .getIDCardInfo(QUERY_IDCARD_KEY, mInputText.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            }
            case QUERY_TEL: {
                mSubscription = Network.getQueryTelApi()
                        .getTelInfo(QUERY_TEL_KEY, mInputText.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            }
        }
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
