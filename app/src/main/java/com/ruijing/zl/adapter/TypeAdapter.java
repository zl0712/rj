package com.ruijing.zl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.views.roundimageview.RoundedImageView;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TypeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.type_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        if (i == 0) holder.tv_hint.setVisibility(View.VISIBLE);
        else if (i == 1) holder.tv_hint.setVisibility(View.INVISIBLE);
        else holder.tv_hint.setVisibility(View.GONE);
        switch (i) {
            case 0:
                holder.imageView.setImageResource(R.mipmap.type_one);
                break;
            case 1:
                holder.imageView.setImageResource(R.mipmap.type_two);
                break;
            case 2:
                holder.imageView.setImageResource(R.mipmap.type_there);
                break;
            case 3:
                holder.imageView.setImageResource(R.mipmap.type_four);
                break;
            case 4:
                holder.imageView.setImageResource(R.mipmap.type_five);
                break;
            case 5:
                holder.imageView.setImageResource(R.mipmap.type_sex);
                break;
            default:
                holder.imageView.setImageResource(R.mipmap.type_sex);
                break;
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) onItemClickListener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        TextView tv_hint;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_icon);
            tv_hint = itemView.findViewById(R.id.tv_hint);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
