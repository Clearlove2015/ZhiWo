package com.zhaoqy.self.api;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhaoqy on 2017/10/11.
 */

public class Network {

    private static QueryApi sQueryTelApi;
    private static QueryApi sQueryIDCardApi;
    private static GirlApi sGirlApi;

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /**
     *  获取查询身份证api
     * @return QueryApi
     */
    public static QueryApi getQueryIDCardApi(){
        if (sQueryIDCardApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sQueryIDCardApi = retrofit.create(QueryApi.class);
        }
        return sQueryIDCardApi;
    }

    /**
     *  获取查询电话号码api
     * @return QueryApi
     */
    public static QueryApi getQueryTelApi(){
        if (sQueryTelApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sQueryTelApi = retrofit.create(QueryApi.class);
        }
        return sQueryTelApi;
    }

    /**
     *  获取妹子api
     * @return GirlApi
     */
    public static GirlApi getGirlApi(){
        if (sGirlApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sGirlApi = retrofit.create(GirlApi.class);
        }
        return sGirlApi;
    }

}
