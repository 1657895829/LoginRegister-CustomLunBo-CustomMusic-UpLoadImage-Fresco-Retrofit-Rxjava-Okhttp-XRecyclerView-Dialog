package com.example.full.presenter;

import com.example.full.bean.HomeBean;
import com.example.full.model.LunBoModel;
import com.example.full.view.LunBoViewCallBack;

/**
 * Created   by   Dewey .
 * 轮播图数据p层
 */
public class LunBoPresenter {
    private LunBoViewCallBack callBack;
    private LunBoModel model;

    public LunBoPresenter(LunBoViewCallBack callBack) {
        this.callBack = callBack;
        this.model = new LunBoModel();
    }

    //请求轮播图数据
    public void getLunBo(){
        model.getLunBo(new LunBoViewCallBack() {
            @Override
            public void success(HomeBean homeBean) {
                callBack.success(homeBean);
            }

            @Override
            public void failure() {
                callBack.failure();
            }
        });
    }

    //取消绑定，防止内存泄露
    public void detach(){
        callBack = null;
    }
}
