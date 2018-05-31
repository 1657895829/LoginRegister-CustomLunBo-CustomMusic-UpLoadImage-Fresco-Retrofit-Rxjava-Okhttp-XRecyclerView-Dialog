package com.example.full;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.full.activity.HomeActivity;
import com.example.full.activity.RegistActivity;
import com.example.full.bean.LoginBean;
import com.example.full.bean.RegistBean;
import com.example.full.presenter.LoginPresenter;
import com.example.full.view.LoginViewCallBack;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  需求描述
    1.实现原生登录注册和个人信息和头像上传。
    2.首页接口，包括轮播图、京东秒杀和为你推荐等业务模块
      点击轮播图，跳转到音乐播放页面，实现一个唱片样式的自定义view
    3.点击首页任意商品，跳转到个人页面
    4.个人页面完成头像上传(系统拍照，相册)图片加载功能。
    5.点击登录退出，完成清空缓存数据所有页面安全退出。
 */

//用户原生登录页面
public class MainActivity extends AppCompatActivity implements LoginViewCallBack {
    @BindView(R.id.login_mobile)
    EditText loginMobile;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_forget)
    TextView loginForget;
    @BindView(R.id.login_regist)
    TextView loginRegist;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.qq_Login)
    ImageButton qq_Login;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //注册EventBus
        EventBus.getDefault().register(this);

        //调用p层
        presenter = new LoginPresenter(this);
    }

    @OnClick({R.id.login_mobile, R.id.login_password, R.id.login_forget, R.id.login_regist, R.id.login_btn, R.id.qq_Login})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            //忘记密码
            case R.id.login_forget:
                break;

            //新用户注册
            case R.id.login_regist:
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(intent);
                break;

            //登录按钮
            case R.id.login_btn:
                if (!TextUtils.isEmpty(loginMobile.getText().toString()) && !TextUtils.isEmpty(loginPassword.getText().toString())) {
                    if (isMobile(loginMobile.getText().toString())) {
                        Toast.makeText(MainActivity.this, "手机号合法！", Toast.LENGTH_SHORT).show();
                        if (loginPassword.getText().toString().length() != 6) {
                            Toast.makeText(MainActivity.this, "密码长度必须是6位！", Toast.LENGTH_SHORT).show();
                        } else {
                            presenter.login(loginMobile.getText().toString(), loginPassword.getText().toString());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "手机号不合法！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                //finish();
                break;

            case R.id.qq_Login:
                Toast.makeText(MainActivity.this,"QQ登录",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 验证手机号码格式
     */
    private static boolean isMobile(String number) {
            /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //表示手机号一共11位，"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    //接收注册的信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msg(RegistBean registBean) {

    }

    @Override
    public void success(LoginBean loginBean) {
        if (loginBean != null) {
            String msg = loginBean.getMsg();
            Toast.makeText(MainActivity.this, msg + "", Toast.LENGTH_SHORT).show();
            if (msg.equals("登录成功")) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void failure() {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    //重写onDestroy方法，避免内存泄露,销毁EventBus
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();

        EventBus.getDefault().unregister(this);
    }
}
