package com.zhaoqy.self.mvp.view;

import com.zhaoqy.self.mvp.model.Girl;

import java.util.List;

/**
 * Created by zhaoqy on 2017/9/28.
 */

public interface GirlView extends MvpView {

    void refresh(List<Girl.ResultsBean> data);

    void loadMore(List<Girl.ResultsBean> data);
}
