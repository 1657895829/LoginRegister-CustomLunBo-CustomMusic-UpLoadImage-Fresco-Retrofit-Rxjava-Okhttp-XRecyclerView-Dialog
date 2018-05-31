package com.example.full.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.full.R;
import com.example.full.bean.HomeBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页输入框下面总的XRecyclerView多条目适配器布局
 */
public class HomeXRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //定义几种不同的类型
    int ONE = 0;   //京东秒杀 倒计时
    int TWO = 1;    //水平滑动的秒杀
    int THREE = 2; //猜你喜欢 一张图片
    int FOUR = 3;  //为你推荐

    static long mHour = 02;
    static long mMin = 15;
    static long mSecond = 36;
    static boolean isRun = true;

    List<HomeBean.MiaoshaBean> listMiaosha;   //秒杀的数据集合
    List<HomeBean.TuijianBean> listTuijian;   //推荐的数据集合

    private Context context;
    private ViewHolder1 viewHolder1;
    private ViewHolder2 viewHolder2;
    private ViewHolder3 viewHolder3;
    private ViewHolder4 viewHolder4;

    private static Handler timeHandler;
    private FragmentManager manager;
    public HomeXRecyclerViewAdapter(Context context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }

    //添加推荐数据的集合
    public void addTuijian(HomeBean.TuijianBean tuijian) {
        if(listTuijian==null){
            listTuijian = new ArrayList<>();
        }
        listTuijian.add(tuijian);
        notifyDataSetChanged();
    }

    //添加秒杀数据的集合
    public void addMiaosha(HomeBean.MiaoshaBean miaosha) {
        if(listMiaosha==null){
            listMiaosha = new ArrayList<>();
        }
        listMiaosha.add(miaosha);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ONE) {        //倒计时
            //如果类型是0
            View view = View.inflate(context, R.layout.shouye_recy_daojishi, null);
            viewHolder1 = new ViewHolder1(view);
            return viewHolder1;
        } else if (viewType == TWO) {//秒杀左滑
            View view = View.inflate(context, R.layout.shouye_recy_miaosha, null);
            viewHolder2 = new ViewHolder2(view);
            return viewHolder2;
        } else if(viewType==THREE){  //一张图片
            View view = View.inflate(context, R.layout.shouye_cainixihuan, null);
            viewHolder3 = new ViewHolder3(view);
            return viewHolder3;
        }else if (viewType == FOUR) {//为你推荐
            View view = View.inflate(context, R.layout.shouye_recy_tuijian, null);
            viewHolder4 = new ViewHolder4(view);
            return viewHolder4;
        }
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {//秒杀倒计时
            startRun();
        }else if (holder instanceof ViewHolder2) {//秒杀左滑
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            viewHolder2.shouye_recy_miaosha.setLayoutManager(linearLayoutManager);
            RecyMiaoshaAdapter miaoshaAdapter = new RecyMiaoshaAdapter(context);
            miaoshaAdapter.addData(listMiaosha);
            viewHolder2.shouye_recy_miaosha.setAdapter(miaoshaAdapter);
            viewHolder2.shouye_recy_miaosha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "cik", Toast.LENGTH_SHORT).show();
                }
            });
        }else if (holder instanceof ViewHolder3) {//一张图片

        } else if (holder instanceof ViewHolder4) {//为你推荐
            ViewHolder4 viewHolder4 = (ViewHolder4) holder;

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);

            viewHolder4.shouye_recy_tuijian.setLayoutManager(gridLayoutManager);
            RecyTuijianAdapter tuijianAdapter = new RecyTuijianAdapter(context);
            tuijianAdapter.addData(listTuijian);
            viewHolder4.shouye_recy_tuijian.setAdapter(tuijianAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return 4;   //几种类型
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return ONE;//1 秒杀倒计时
        } else if (position == 1) {
            return TWO;//2 秒杀
        }else if(position==2){
            return THREE;  //3 猜你喜欢
        }else if(position==3){
            return FOUR;  //4 推荐
        }

        return ONE;
    }

    /**
     * 开启倒计时
     * */
    private void startRun(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRun){
                    try {
                        //睡眠一秒发送消息handler
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.what=1;
                        //发送消息
                        timeHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //秒杀倒计时的布局
    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        //控制倒计时的显示
        TextView tvHour;//倒计时的小时
        TextView tvMinute;//分钟
        TextView tvSecond;//秒
        @SuppressLint("HandlerLeak")
        public ViewHolder1(View itemView) {
            super(itemView);
            tvHour = (TextView) itemView.findViewById(R.id.tv_hour);
            tvMinute = (TextView) itemView.findViewById(R.id.tv_minute);
            tvSecond = (TextView) itemView.findViewById(R.id.tv_second);


            //调用 倒计时计算的方法
            timeHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what==1){
                        //调用 倒计时计算的方法
                        computeTime();
                        if(mHour<10){
                            tvHour.setText("0"+mHour+"");
                        }else{
                            tvHour.setText(mHour+"");
                        }
                        if(mMin<10){
                            tvMinute.setText("0"+mMin+"");
                        }else{
                            tvMinute.setText(mMin+"");
                        }if(mSecond<10){
                            tvSecond.setText("0"+mSecond+"");
                        }else{
                            tvSecond.setText(mSecond+"");
                        }
                    }
                }
            };

        }

    }

    /**
     * 倒计时计算
     * */
    private static void computeTime(){
        //首先把秒减1
        mSecond--;
        if(mSecond<0){//如果秒已经减到了0
            mMin--;//分钟就减1
            mSecond=59;//秒变成 59
            if(mMin<0){//如果分钟小于0
                mMin=59;//分钟变成59
                mHour--;//小时减1
            }
        }
    }
    //秒杀的布局
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        private final RecyclerView shouye_recy_miaosha;

        public ViewHolder2(View itemView) {
            super(itemView);
            shouye_recy_miaosha = (RecyclerView) itemView.findViewById(R.id.shouye_recy_miaosha);

        }
    }

    //猜你喜欢的图片
    public static class ViewHolder3 extends RecyclerView.ViewHolder {

        public ViewHolder3(View itemView) {
            super(itemView);

        }
    }

    //为你推荐的布局
    public static class ViewHolder4 extends RecyclerView.ViewHolder {
        private final RecyclerView shouye_recy_tuijian;

        public ViewHolder4(View itemView) {
            super(itemView);

            shouye_recy_tuijian = (RecyclerView) itemView.findViewById(R.id.shouye_recy_tuijian);
        }
    }

}

