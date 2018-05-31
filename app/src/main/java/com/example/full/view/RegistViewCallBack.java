package com.example.full.view;

import com.example.full.bean.RegistBean;

/**
 * 注册view层接口
 */
public interface RegistViewCallBack {
    public void success(RegistBean registBean);
    public void failure();
}
