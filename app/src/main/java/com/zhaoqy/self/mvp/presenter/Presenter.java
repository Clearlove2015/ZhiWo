package com.zhaoqy.self.mvp.presenter;

import com.zhaoqy.self.mvp.view.MvpView;

/**
 * Created by zhaoqy on 2017/9/28.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
