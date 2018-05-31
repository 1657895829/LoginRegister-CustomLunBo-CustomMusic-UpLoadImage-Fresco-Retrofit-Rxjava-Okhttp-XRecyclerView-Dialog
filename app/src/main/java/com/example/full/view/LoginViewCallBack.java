package com.example.full.view;


import com.example.full.bean.LoginBean;

/**
 * 登录view层接口
 */
public interface LoginViewCallBack {
    public void success(LoginBean loginBean);
    public void failure();
}
