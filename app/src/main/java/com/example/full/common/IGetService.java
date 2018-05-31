package com.example.full.common;

import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 封装的网络数据接口请求类
 */

public interface IGetService {

    //注册：http://120.27.23.105/user/reg?mobile=13534245633&password=55555554
    @GET
    Observable<String> regist(@Url String url, @QueryMap Map<String, String> map);

    //get请求方式，传入网址url，Map集合传参，使用Observer被观察者订阅执行
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, String> map);

    //get请求方式，传入网址url，不传参，直接使用Observer被观察者订阅执行
    @GET
    Observable<String> get(@Url String url);

    //post请求方式，传入网址url，Map集合传参，使用Observer被观察者订阅执行
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, String> map);

}
