package com.example.full.presenter;

import com.example.full.bean.RegistBean;
import com.example.full.model.RegistModel;
import com.example.full.view.RegistViewCallBack;

/**
 * 注册p层
 */

public class RegistPresenter {
    RegistViewCallBack callBack;
    private final RegistModel model;

    public RegistPresenter(RegistViewCallBack callBack) {
        this.callBack = callBack;
        model = new RegistModel();
    }

    //注册的方法
    public void regist(String mobile,String password){
        model.regist(mobile, password, new RegistViewCallBack() {
            @Override
            public void success(RegistBean registBean) {
                callBack.success(registBean);
            }

            @Override
            public void failure() {
                callBack.failure();
            }
        });
    }

    //销毁view层
    public void detach(){
        this.callBack = null;
    }
}
