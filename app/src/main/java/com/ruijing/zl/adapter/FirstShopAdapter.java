package com.ruijing.zl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruijing.zl.R;
import com.ruijing.zl.bean.FirstShopBean;
import com.ruijing.zl.views.roundimageview.RoundedImageView;

import java.util.List;

public class FirstShopAdapter extends RecyclerView.Adapter<FirstShopAdapter.MyViewHolder> {
    private Context context;
    private List<FirstShopBean.Shop> mData;

    public FirstShopAdapter(Context context, List<FirstShopBean.Shop> data) {
        this.context = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_two, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        FirstShopBean.Shop shop = mData.get(i + 1);
        holder.iv_icon.setImageResource(R.mipmap.shop_icon);
        //Glide.with(context).load(shop.getUrl()).into(holder.iv_icon);
        holder.tv_title.setText(shop.getName());
        holder.tv_price.setText("￥" + shop.getPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size() - 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_icon;
        TextView tv_title, tv_price, tv_buy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_buy = itemView.findViewById(R.id.tv_buy);
            iv_icon.setCornerRadius(5);
        }
    }
}
