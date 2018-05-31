package com.example.full.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.example.full.R;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created   by   Dewey .
 * 轮播图使用自定义view实现
 */
public class MyGroupView extends LinearLayout {
    private ViewPager viewPager;
    private int item = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(item);
        }
    };

    public MyGroupView(Context context) {
        this(context,null);
    }

    public MyGroupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = View.inflate(context, R.layout.customview, this);
        viewPager = inflate.findViewById(R.id.vp);
    }

    //设置轮播时间
    public void tim() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ++item;
                handler.sendEmptyMessage(0);
            }
        },0,3000);
    }

    //设置轮播图适配器
    public void setAdapter(PagerAdapter pagerAdapter){
        viewPager.setAdapter(pagerAdapter);
    }
}
