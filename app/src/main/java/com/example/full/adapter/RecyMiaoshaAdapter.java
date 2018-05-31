package com.example.full.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.full.R;
import com.example.full.activity.MineActivity;
import com.example.full.bean.HomeBean;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀图片集合适配器
 */
public class RecyMiaoshaAdapter extends RecyclerView.Adapter<RecyMiaoshaAdapter.TuijianViewHolder>{
    List<HomeBean.MiaoshaBean> listDa;
    Context context;
    public RecyMiaoshaAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<HomeBean.MiaoshaBean> list) {
        if(listDa==null){
            listDa = new ArrayList<>();
        }
        listDa.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public TuijianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shouye_recy_miaosha_item,null);
        TuijianViewHolder tuijianViewHolder = new TuijianViewHolder(view);
        return tuijianViewHolder;
    }

    @Override
    public void onBindViewHolder(TuijianViewHolder holder, final int position) {
        if(listDa.size()>0) {
            if(listDa.get(0).getList().get(position).getImages().contains("|")) {
                String[] split = listDa.get(0).getList().get(position).getImages().split("\\|");
                holder.miaosha_image.setImageURI(split[0]);
                holder.miaosha_text.setText(listDa.get(0).getList().get(position).getTitle());

            }else {
                holder.miaosha_image.setImageURI(listDa.get(0).getList().get(position).getImages());
                holder.miaosha_text.setText(listDa.get(0).getList().get(position).getTitle());

            }
        }

        //条目的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传递商品数据
                Intent intent = new Intent(context, MineActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDa==null?0:listDa.get(0).getList().size();
    }

    public static class TuijianViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView miaosha_image;
        private final TextView miaosha_text;
        public TuijianViewHolder(View itemView) {
            super(itemView);
            miaosha_image = (SimpleDraweeView) itemView.findViewById(R.id.miaosha_image);
            miaosha_text = (TextView) itemView.findViewById(R.id.miaosha_text);
        }
    }
}
