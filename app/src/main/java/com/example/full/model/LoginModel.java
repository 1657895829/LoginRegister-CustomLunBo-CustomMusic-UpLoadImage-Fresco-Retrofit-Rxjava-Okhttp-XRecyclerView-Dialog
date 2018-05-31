package com.example.full.model;
import com.example.full.bean.LoginBean;
import com.example.full.common.APIFactory;
import com.example.full.common.AbstractObserver;
import com.example.full.view.LoginViewCallBack;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录model层
 */

public class LoginModel {
    public void login(String mobile, String password, final LoginViewCallBack callBack){
        //http://120.27.23.105/user/login?mobile=15810680939&password=123456
        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("mobile",mobile);
        map.put("password",password);

        //调用单例模式封装类
        APIFactory.getInstance().regist("/user/login", map, new AbstractObserver<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {
                callBack.success(loginBean);
            }

            @Override
            public void onFailure() {
                callBack.failure();
            }
        });
    }
}
