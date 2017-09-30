package com.zhaoqy.self.mvp.view;

import android.view.View;

/**
 * Created by zhaoqy on 2017/9/28.
 */

public interface MvpView {

    void showLoading(String msg);

    void hideLoading();

    void showError(String msg);

    void showError(String msg, View.OnClickListener onClickListener);
}
