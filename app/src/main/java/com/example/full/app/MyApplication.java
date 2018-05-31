package com.example.full.app;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fresco  Retrofit  的初始化全局配置类
 */
public class MyApplication extends Application {

    //抽取为公共变量
/*    public static GetDataInterface retrofit;*/

    @Override
    public void onCreate() {
        super.onCreate();

        //Fresco
        Fresco.initialize(this);

/*        //Retrofit
        Retrofit request  = new Retrofit.Builder()
                .baseUrl("http://api.tianapi.com")
                .addConverterFactory(GsonConverterFactory.create())

        //将请求对象转化为被观察者模式    Observerable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //创建网络接口数据的请求
        retrofit = request.create(GetDataInterface.class);*/
    }
}
