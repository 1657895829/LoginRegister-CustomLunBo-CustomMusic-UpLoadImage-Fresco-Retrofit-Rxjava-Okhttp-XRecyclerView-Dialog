package com.example.full.common;

import java.util.Map;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册或登录封装的工厂模式
 */

public class APIFactory {
    private APIFactory(){}
    private static APIFactory factory = null;

    public static APIFactory getInstance(){
        if(factory==null){
            synchronized (APIFactory.class){
                if(factory==null){
                    factory = new APIFactory();
                }
            }
        }
        return factory;
    }

    //注册或登录封装方法
    public void regist(String url, Map<String,String> map, Observer<String> observer){

        RetrofitUtils.getInstance().regist(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    //get请求方式，传入网址url，Map集合传参，使用Observer被观察者订阅执行
    public void get(String url, Map<String,String> map, Observer<String> observer){
        RetrofitUtils.getInstance().get(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //get请求方式，传入网址url，不传参，直接使用Observer被观察者订阅执行
    public void get(String url,Observer<String> observer){
        RetrofitUtils.getInstance().get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //post请求方式，传入网址url，Map集合传参，使用Observer被观察者订阅执行
    public void post(String url,Map<String,String> map,Observer<String> observer){

        RetrofitUtils.getInstance().post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
