package com.example.full.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.full.MainActivity;
import com.example.full.R;
import com.example.full.bean.RegistBean;
import com.example.full.presenter.RegistPresenter;
import com.example.full.view.RegistViewCallBack;
import org.greenrobot.eventbus.EventBus;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//新用户注册页面
public class RegistActivity extends AppCompatActivity implements RegistViewCallBack {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.regest_mobile)
    EditText regestMobile;
    @BindView(R.id.regest_password)
    EditText regestPassword;
    @BindView(R.id.regest_qurenpassword)
    EditText regestQurenpassword;
    @BindView(R.id.regest_email)
    EditText regestEmail;
    @BindView(R.id.regest_btn)
    Button regestBtn;
    private RegistPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        //调用p层
        presenter = new RegistPresenter(this);
    }

    @OnClick({R.id.back, R.id.regest_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            //返回登录页
            case R.id.back:
                finish();
                break;

            //注册按钮
            case R.id.regest_btn:
                //输入内容不得为空
                if (!TextUtils.isEmpty(regestMobile.getText().toString()) && !TextUtils.isEmpty(regestPassword.getText().toString()) &&
                    !TextUtils.isEmpty(regestQurenpassword.getText().toString()) && !TextUtils.isEmpty(regestEmail.getText().toString())){

                    //手机号
                    if (isMobile(regestMobile.getText().toString())){
                        Toast.makeText(RegistActivity.this, "手机号合法！", Toast.LENGTH_SHORT).show();

                        //密码长度必须是6位
                        if (regestPassword.getText().toString().length() != 6 || regestQurenpassword.getText().toString().length() != 6){
                            Toast.makeText(RegistActivity.this, "密码长度必须是6位！", Toast.LENGTH_SHORT).show();
                        }
                        //两次密码输入不一致
                        else if (!regestPassword.getText().toString().equals(regestQurenpassword.getText().toString())){
                            Toast.makeText(RegistActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();

                            //邮箱格式
                            if (!isEmail(regestEmail.getText().toString())){
                                Toast.makeText(RegistActivity.this, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            presenter.regist(regestMobile.getText().toString(), regestPassword.getText().toString());
                        }

                    }else {
                        Toast.makeText(RegistActivity.this, "手机号不合法！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "个人信息必须完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 验证手机号码格式
     */
    private static boolean  isMobile(String number) {
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

    /**
     * java-正则表达式判断 Email邮箱 是否合法
     * @param email
     * @return
     *
     * pattern 属性规定用于验证输入字段的模式。
     */
    public static boolean isEmail(String email){
        if (null == email || "".equals(email)) {
            return false;
        }

        //简单匹配
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");

        //复杂匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void success(RegistBean registBean) {
        if (registBean != null){
            String msg = registBean.getMsg();
            Toast.makeText(RegistActivity.this, msg + "",Toast.LENGTH_SHORT).show();

            if (msg.equals("注册成功")){
                //发送消息
                EventBus.getDefault().post(registBean);
                Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void failure() {
        Toast.makeText(RegistActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
    }

    //重写onDestroy方法，避免内存泄露
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
