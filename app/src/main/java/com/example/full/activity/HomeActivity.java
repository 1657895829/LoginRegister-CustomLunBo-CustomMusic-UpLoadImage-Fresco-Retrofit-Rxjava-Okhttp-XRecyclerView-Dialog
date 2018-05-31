package com.example.full.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import com.example.full.R;
import com.example.full.adapter.HomeXRecyclerViewAdapter;
import com.example.full.adapter.PageAdapter;
import com.example.full.bean.HomeBean;
import com.example.full.bean.VpBean;
import com.example.full.custom.MyGroupView;
import com.example.full.presenter.LunBoPresenter;
import com.example.full.view.LunBoViewCallBack;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

//实现首页接口，包括轮播图、京东秒杀和为你推荐等业务模块 (不适用基类时继承 )
public class HomeActivity extends AppCompatActivity implements LunBoViewCallBack {
    private ArrayList<VpBean> list = new ArrayList<VpBean>();
    private HomeXRecyclerViewAdapter homeAdapter;
    @BindView(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.myView)
    MyGroupView myView;
    private LunBoPresenter presenter;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            HomeBean jsonBean = gson.fromJson((String) msg.obj, HomeBean.class);
            String s = jsonBean.toString();
            System.out.println("数据：" + s);
            List<HomeBean.DataBean> data = jsonBean.getData();
            for (int i = 0; i < data.size(); i++) {
                String img = data.get(i).getIcon();
                list.add(new VpBean(img));
                VpBean bean = new VpBean(img);
                System.out.println("VpBean：" + bean.toString());
            }

            //设置自定义轮播图view视图适配器
            myView.setAdapter(new PageAdapter(list, HomeActivity.this));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        //展示自定义轮播图view
        initData();
        myView.tim();

        //展示首页数据
        homeAdapter = new HomeXRecyclerViewAdapter(HomeActivity.this, getSupportFragmentManager());
        LinearLayoutManager manager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(manager);
        presenter = new LunBoPresenter(this);
        presenter.getLunBo();

        //XRecyclerview的上拉下拉方法
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //在子线程内完成下拉加载数据
                        homeAdapter.notifyDataSetChanged();
                        xRecyclerView.refreshComplete();
                    }
                }, 888);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //在子线程内完成下拉加载数据
                        homeAdapter.notifyDataSetChanged();
                        xRecyclerView.loadMoreComplete();
                    }
                }, 888);
            }
        });
    }

    //请求数据的方法
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://120.27.23.105/ad/getAd");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String stringTemp = "";
                        while ((stringTemp = reader.readLine()) != null) {
                            builder.append(stringTemp);
                        }
                        Message message = new Message();
                        message.obj = builder.toString();
                        handler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void success(HomeBean homeBean) {
        System.out.println("数据：" + homeBean.toString());

        //添加数据
        homeAdapter.addTuijian(homeBean.getTuijian());
        homeAdapter.addMiaosha(homeBean.getMiaosha());

        //将集合传给适配器
        xRecyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void failure() {

    }

    //解绑，避免内存泄露
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
