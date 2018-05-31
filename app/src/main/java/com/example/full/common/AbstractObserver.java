package com.example.full.common;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer  观察者模式
 */

public abstract class AbstractObserver<T> implements Observer<String> {

    //不需要下面重写的四个方法,只需要自己写的两个抽象方法
    public abstract void onSuccess(T t);
    public abstract void onFailure();


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String result) {
        //这里的s是请求网络传回来的string字符串
        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        Class clazz = (Class) types[0];//强转成类对象
        //使用gson解析
        Gson gson = new Gson();
        T t = (T) gson.fromJson(result,clazz);

        //调用抽象方法onSuccess
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure();
    }

    @Override
    public void onComplete() {

    }
}
