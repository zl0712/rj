package com.ruijing.zl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ruijing.zl.R;
import com.ruijing.zl.views.carousel.CarouselPagerAdapter;
import com.ruijing.zl.views.carousel.CarouselViewPager;
import com.ruijing.zl.views.roundimageview.RoundedImageView;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */

public class ImagePagerAdapter extends CarouselPagerAdapter<CarouselViewPager> {
    private Context context;
    private List<String> urls;
    private LinkedList<View> mViewCache = null;
    private View.OnClickListener onClickListener;

    public ImagePagerAdapter(Context context, List<String> urls, CarouselViewPager viewPager, View.OnClickListener onClickListener) {
        super(viewPager);
        this.context = context;
        this.urls = urls;
        this.onClickListener = onClickListener;
        this.mViewCache = new LinkedList<>();
    }

    @Override
    public Object instantiateRealItem(ViewGroup container, final int position) {
        View view = null;
        ViewHolder viewHolder = null;
        if (mViewCache.size() == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_image_show, null);
            viewHolder = new ViewHolder();
            //viewHolder.cardView = (CardView) view.findViewById(R.id.cv_show);
            //viewHolder.cardView.setCardElevation(10f);
            //viewHolder.cardView.setRadius(10f);
            //viewHolder.cardView.setPreventCornerOverlap(false);
            viewHolder.riv_show = view.findViewById(R.id.riv_show);
            viewHolder.riv_show.setCornerRadius(5);
            viewHolder.rl_image = view.findViewById(R.id.rl_image);
            viewHolder.rl_image.setOnClickListener(onClickListener);
            view.setTag(viewHolder);
        } else {
            view = mViewCache.removeFirst();
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.rl_image.setTag(position);
        //Glide.with(context)
        //.load(urls.get(position)).asBitmap().placeholder(R.mipmap.nopicture_big).diskCacheStrategy(DiskCacheStrategy.RESULT).into(viewHolder.riv_show);
        //Glide.with(context).load(urls.get(position)).into(viewHolder.riv_show);
        viewHolder.riv_show.setImageResource(R.mipmap.shop_icon);
        container.addView(view);
        return view;
    }

    public final class ViewHolder {
        //CardView cardView ;
        RoundedImageView riv_show;
        RelativeLayout rl_image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        this.mViewCache.add((View) object);
    }

    @Override
    public int getRealDataCount() {
        return urls != null ? urls.size() : 0;
    }
}
