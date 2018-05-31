package com.example.full.model;

import com.example.full.bean.HomeBean;
import com.example.full.common.APIFactory;
import com.example.full.common.AbstractObserver;
import com.example.full.view.LunBoViewCallBack;

/**
 * Created   by   Dewey .
 * 轮播图  请求数据model层
 */

public class LunBoModel {
    //调用封装的请求方式的单例模式执行类请求接口
    public void getLunBo(final LunBoViewCallBack callBack){
        APIFactory.getInstance().get("/ad/getAd", new AbstractObserver<HomeBean>() {
            @Override
            public void onSuccess(HomeBean homeBean) {
                System.out.println("model层数据："+homeBean.toString());
                callBack.success(homeBean);
            }

            @Override
            public void onFailure() {
                callBack.failure();
            }
        });
    }
}
