package com.zhaoqy.self.mvp;

import com.zhaoqy.self.mvp.model.Girl;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhaoqy on 2017/9/28.
 */

public interface Api {

    @GET("data/福利/{num}/{page}")
    Observable<Girl> getPictures(@Path("num") int num, @Path("page") int page);
}
