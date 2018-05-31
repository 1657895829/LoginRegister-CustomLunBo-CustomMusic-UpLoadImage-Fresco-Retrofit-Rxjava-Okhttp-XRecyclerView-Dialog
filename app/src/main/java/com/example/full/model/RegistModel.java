package com.example.full.model;
import com.example.full.bean.RegistBean;
import com.example.full.common.APIFactory;
import com.example.full.common.AbstractObserver;
import com.example.full.view.RegistViewCallBack;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册model层
 */

public class RegistModel {
    public void regist(String mobile, String password, final RegistViewCallBack callBack){
        //http://120.27.23.105/user/reg?mobile=13534245633&password=55555554
        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("mobile",mobile);
        map.put("password",password);
        APIFactory.getInstance().regist("/user/reg", map, new AbstractObserver<RegistBean>() {
            @Override
            public void onSuccess(RegistBean registBean) {
                callBack.success(registBean);
            }

            @Override
            public void onFailure() {
                callBack.failure();
            }
        });
    }
}
