package com.example.full.common;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * RetrofitUtils
 */

public class RetrofitUtils {
    private static IGetService service = null;

    //单例模式
    public static IGetService getInstance(){
        if(service==null){
            synchronized (RetrofitUtils.class){
                if(service==null){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://120.27.23.105")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(OkHttpUtils.getInstance())
                            .build();

                    service = retrofit.create(IGetService.class);
                }
            }
        }
        return service;
    }
}
