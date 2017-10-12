package com.zhaoqy.self.api;

import com.zhaoqy.self.bean.query.QueryIDCard;
import com.zhaoqy.self.bean.query.QueryTel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhaoqy on 2017/10/11.
 */

public interface QueryApi {

    //    http://apis.juhe.cn/mobile/get   phone	int
    /*{
        "resultcode":"200",
            "reason":"Return Successd!",
            "result":{
            "province":"广东",
            "city":"深圳",
            "areacode":"0755",
            "zip":"18000",
            "company":"移动",
            "card":""
    },
        "error_code":0
    }*/

    @GET("mobile/get")
    Observable<QueryTel> getTelInfo(@Query("key") String key, @Query("phone") String phone);


    // http://apis.juhe.cn/idcard/index  cardno	string
    /*{
        "resultcode":"200",
            "reason":"成功的返回",
            "result":{
            "area":"湖北省黄石市阳新县",
            "sex":"男",
            "birthday":"1988年05月12日",
            "verify":""
    },
        "error_code":0
    }*/

    @GET("idcard/index")
    Observable<QueryIDCard> getIDCardInfo(@Query("key") String key, @Query("cardno") String cardno);
}
