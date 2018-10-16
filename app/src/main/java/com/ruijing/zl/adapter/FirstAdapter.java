package com.ruijing.zl.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruijing.zl.R;
import com.ruijing.zl.bean.FirstShopBean;
import com.ruijing.zl.utils.ScreenUtil;
import com.ruijing.zl.views.ItemDecoration;
import com.ruijing.zl.views.roundimageview.RoundedImageView;

import java.util.List;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.MyViewHolder> {
    private Context context;
    private List<FirstShopBean> mData;
    private OnItemClickListener onItemClickListener;

    public FirstAdapter(Context context, List<FirstShopBean> data) {
        this.context = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        final FirstShopBean firstShopBean = mData.get(i);
        holder.tv_name.setText(firstShopBean.getName());
        if (firstShopBean.getList() != null && firstShopBean.getList().size() != 0) {
            FirstShopBean.Shop shop = firstShopBean.getList().get(0);
            holder.tv_title.setText(shop.getName());
            holder.tv_content.setText(shop.getContent());
            holder.tv_price.setText("￥" + shop.getPrice());
            //Glide.with(context).load(shop.getUrl()).into(holder.iv_icon);
            holder.iv_icon.setImageResource(R.mipmap.shop_icon);
            if (firstShopBean.getList().size() > 1) {
                holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                holder.recyclerView.setNestedScrollingEnabled(false);
                //holder.recyclerView.addItemDecoration(new ItemDecoration(LinearLayoutManager.HORIZONTAL, (int) 1, Color.parseColor("#eeeeee"), 10));
                FirstShopAdapter firstShopAdapter = new FirstShopAdapter(context, firstShopBean.getList());
                holder.recyclerView.setAdapter(firstShopAdapter);
            }
        }

        holder.iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) onItemClickListener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tv_name, tv_title, tv_content, tv_price, tv_buy;
        RoundedImageView iv_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerview);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_buy = itemView.findViewById(R.id.tv_buy);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            iv_icon.setCornerRadius(5);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
