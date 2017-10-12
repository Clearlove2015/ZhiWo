package com.zhaoqy.self.mvp.presenter;

import com.zhaoqy.self.api.Network;
import com.zhaoqy.self.mvp.model.Girl;
import com.zhaoqy.self.mvp.view.GirlView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoqy on 2017/9/28.
 */

public class GirlPresenter extends BasePresenter<GirlView> {

    @Override
    public void attachView(GirlView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void getGirlPic(final int page) {
        Network.getGirlApi()
                .getPictures(10, page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Girl, List<Girl.ResultsBean>>() {

                    @Override
                    public List<Girl.ResultsBean> call(Girl girlsEntity) {
                        return girlsEntity.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl.ResultsBean>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError("网络错误！");
                    }

                    @Override
                    public void onNext(List<Girl.ResultsBean> resultsBeen) {
                        if (page == 1) {
                            getMvpView().refresh(resultsBeen);
                        } else {
                            getMvpView().loadMore(resultsBeen);
                        }
                    }
                });
    }
}
