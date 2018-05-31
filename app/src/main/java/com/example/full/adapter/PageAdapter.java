package com.example.full.adapter;

import android.support.v4.view.PagerAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.full.R;
import com.example.full.activity.MusicActivity;
import com.example.full.bean.VpBean;
import java.util.ArrayList;
/**
 * Created   by   Dewey .
 * 轮播图适配器
 */
public class PageAdapter extends PagerAdapter {
    private ArrayList<VpBean> list;
    private Context context;

    public PageAdapter(ArrayList list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View inflate = View.inflate(context, R.layout.image, null);
        ImageView img = (ImageView) inflate.findViewById(R.id.img);
        Glide.with(context).load(list.get(position % list.size()).getImg()).into(img);

        //点击轮播图，跳转到一个唱片样式的自定义view播放音乐
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicActivity.class);
                context.startActivity(intent);
            }
        });
        container.addView(inflate);
        return inflate;
    }
}
