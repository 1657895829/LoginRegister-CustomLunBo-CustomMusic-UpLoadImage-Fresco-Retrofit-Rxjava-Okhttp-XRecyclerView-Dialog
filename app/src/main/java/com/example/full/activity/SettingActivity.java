package com.example.full.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.full.MainActivity;
import com.example.full.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设置页面
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.outLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:   //点击叉号，返回上一页面
                finish();
                break;

            case R.id.outLogin: //退出登录
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();
                break;

            default:
                break;
        }
    }
}

