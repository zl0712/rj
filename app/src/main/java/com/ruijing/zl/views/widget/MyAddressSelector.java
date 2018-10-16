package com.ruijing.zl.views.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.bean.ProvinceBean;
import com.ruijing.zl.utils.Lists;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by smartTop on 2016/10/19.
 */

public class MyAddressSelector implements AdapterView.OnItemClickListener {
    private static final int INDEX_TAB_PROVINCE = 0;//省份标志
    private static final int INDEX_TAB_CITY = 1;//城市标志
    private static final int INDEX_TAB_COUNTY = 2;//乡镇标志
    private int tabIndex = INDEX_TAB_PROVINCE; //默认是省份

    private static final int INDEX_INVALID = -1;
    private int provinceIndex = INDEX_INVALID; //省份的下标
    private int cityIndex = INDEX_INVALID;//城市的下标
    private int countyIndex = INDEX_INVALID;//乡镇的下标

    private Context context;
    private final LayoutInflater inflater;
    private View view;

    private View indicator;

    private LinearLayout layout_tab;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private RelativeLayout rl_title;
    private View v_line_title;

    private ProgressBar progressBar;

    private ListView listView;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CountyAdapter countyAdapter;
    private List<ProvinceBean> provinces;
    private List<ProvinceBean> cities;
    private List<ProvinceBean> counties;
    private OnAddressSelectedListener listener;
    private OnDialogCloseListener dialogCloseListener;

    private static final int WHAT_PROVINCES_PROVIDED = 0;
    private static final int WHAT_CITIES_PROVIDED = 1;
    private static final int WHAT_COUNTIES_PROVIDED = 2;
    private ImageView iv_colse;
    private int selectedColor;
    private int unSelectedColor;
    private int provinceId;
    private int cityId;
    private int areaId;
    private List<ProvinceBean> queryBean;

    @SuppressWarnings("unchecked")
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_PROVINCES_PROVIDED: //更新省份列表
                    provinces = (List<ProvinceBean>) msg.obj;
                    provinceAdapter.notifyDataSetChanged();
                    listView.setAdapter(provinceAdapter);

                    break;

                case WHAT_CITIES_PROVIDED: //更新城市列表
                    cities = (List<ProvinceBean>) msg.obj;
                    cityAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(cities)) {
                        // 以次级内容更新列表
                        listView.setAdapter(cityAdapter);
                        // 更新索引为次级
                        tabIndex = INDEX_TAB_CITY;
                    } else {
                        // 次级无内容，回调
                        callbackInternal();
                    }
                    break;

                case WHAT_COUNTIES_PROVIDED://更新乡镇列表
                    counties = (List<ProvinceBean>) msg.obj;
                    countyAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(counties)) {
                        listView.setAdapter(countyAdapter);
                        tabIndex = INDEX_TAB_COUNTY;
                    } else {
                        callbackInternal();
                    }

                    break;
                case 3:
                    retrieveProvinces();
                    break;
            }

            updateTabsVisibility();
            updateProgressVisibility();
            updateIndicator();

            return true;
        }
    });
    private List<ProvinceBean> privinceList;
    private List<ProvinceBean> cityList;
    private List<ProvinceBean> counityList;


    public MyAddressSelector(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        initViews();
        initAdapters();
        retrieveProvinces();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        privinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        counityList = new ArrayList<>();
        view = inflater.inflate(R.layout.my_address_selector, null);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);//进度条
        this.iv_colse = (ImageView) view.findViewById(R.id.iv_colse);
        this.listView = (ListView) view.findViewById(R.id.listView);//listview
        this.indicator = view.findViewById(R.id.indicator); //指示器
        this.layout_tab = (LinearLayout) view.findViewById(R.id.layout_tab);
        this.textViewProvince = (TextView) view.findViewById(R.id.textViewProvince);//省份
        this.textViewCity = (TextView) view.findViewById(R.id.textViewCity);//城市
        this.textViewCounty = (TextView) view.findViewById(R.id.textViewCounty);//区 乡镇
        this.rl_title = view.findViewById(R.id.rl_title);
        this.v_line_title = view.findViewById(R.id.v_line_title);

        this.textViewProvince.setOnClickListener(new OnProvinceTabClickListener());
        this.textViewCity.setOnClickListener(new OnCityTabClickListener());
        this.textViewCounty.setOnClickListener(new OnCountyTabClickListener());

        this.listView.setOnItemClickListener(this);
        this.iv_colse.setOnClickListener(new onCloseClickListener());

        updateIndicator();
    }

    public void setXuanzexiaoqu() {
        rl_title.setVisibility(View.GONE);
        v_line_title.setVisibility(View.GONE);
    }

    /**
     * 设置字体选中的颜色
     */
    public void setTextSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * 设置字体没有选中的颜色
     */
    public void setTextUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    /**
     * 设置字体的大小
     */
    public void setTextSize(float dp) {
        textViewProvince.setTextSize(dp);
        textViewCity.setTextSize(dp);
        textViewCounty.setTextSize(dp);
    }

    /**
     * 设置字体的背景
     */
    public void setBackgroundColor(int colorId) {
        layout_tab.setBackgroundColor(context.getResources().getColor(colorId));
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(int colorId) {
        indicator.setBackgroundColor(context.getResources().getColor(colorId));
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(String color) {
        indicator.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 初始化adapter
     */
    private void initAdapters() {
        provinceAdapter = new ProvinceAdapter();
        cityAdapter = new CityAdapter();
        countyAdapter = new CountyAdapter();
    }

    /**
     * 更新tab 指示器
     */
    private void updateIndicator() {
        view.post(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case INDEX_TAB_PROVINCE: //省份
                        buildIndicatorAnimatorTowards(textViewProvince).start();
                        break;
                    case INDEX_TAB_CITY: //城市
                        buildIndicatorAnimatorTowards(textViewCity).start();
                        break;
                    case INDEX_TAB_COUNTY: //乡镇
                        buildIndicatorAnimatorTowards(textViewCounty).start();
                        break;
                }
            }
        });
    }

    /**
     * tab 来回切换的动画
     *
     * @param tab
     * @return
     */
    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }

    /**
     * 更新tab显示
     */
    private void updateTabsVisibility() {
        textViewProvince.setVisibility(Lists.notEmpty(provinces) ? View.VISIBLE : View.GONE);
        textViewCity.setVisibility(Lists.notEmpty(cities) ? View.VISIBLE : View.GONE);
        textViewCounty.setVisibility(Lists.notEmpty(counties) ? View.VISIBLE : View.GONE);

        //按钮能不能点击 false 不能点击 true 能点击
        textViewProvince.setEnabled(tabIndex != INDEX_TAB_PROVINCE);
        textViewCity.setEnabled(tabIndex != INDEX_TAB_CITY);
        textViewCounty.setEnabled(tabIndex != INDEX_TAB_COUNTY);

        if (selectedColor != 0 && unSelectedColor != 0) {
            updateTabTextColor();
        }
    }

    /**
     * 更新字体的颜色
     */
    private void updateTabTextColor() {
        if (tabIndex != INDEX_TAB_PROVINCE) {
            textViewProvince.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewProvince.setTextColor(context.getResources().getColor(unSelectedColor));
        }
        if (tabIndex != INDEX_TAB_CITY) {
            textViewCity.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewCity.setTextColor(context.getResources().getColor(unSelectedColor));
        }
        if (tabIndex != INDEX_TAB_COUNTY) {
            textViewCounty.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewCounty.setTextColor(context.getResources().getColor(unSelectedColor));
        }
    }

    /**
     * 点击省份的监听
     */
    class OnProvinceTabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_PROVINCE;
            listView.setAdapter(provinceAdapter);

            if (provinceIndex != INDEX_INVALID) {
                listView.setSelection(provinceIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击城市的监听
     */
    class OnCityTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_CITY;
            listView.setAdapter(cityAdapter);

            if (cityIndex != INDEX_INVALID) {
                listView.setSelection(cityIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击区 乡镇的监听
     */
    class OnCountyTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_COUNTY;
            listView.setAdapter(countyAdapter);

            if (countyIndex != INDEX_INVALID) {
                listView.setSelection(countyIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击右边关闭dialog监听
     */
    class onCloseClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (dialogCloseListener != null) {
                dialogCloseListener.dialogclose();
                //callbackInternal();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (tabIndex) {
            case INDEX_TAB_PROVINCE: //省份
                ProvinceBean province = provinceAdapter.getItem(position);

                // 更新当前级别及子级标签文本
                textViewProvince.setText(province.getAreaname());
                textViewCity.setText("请选择");
                textViewCounty.setText("请选择");
                //根据省份的id,从数据库中查询城市列表
                retrieveCitiesWith(province.getId());

                // 清空子级数据
                cities = null;
                counties = null;
                cityAdapter.notifyDataSetChanged();
                countyAdapter.notifyDataSetChanged();
                // 更新已选中项
                this.provinceIndex = position;
                this.cityIndex = INDEX_INVALID;
                this.countyIndex = INDEX_INVALID;
                // 更新选中效果
                provinceAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_CITY://城市
                ProvinceBean city = cityAdapter.getItem(position);
                textViewCity.setText(city.getAreaname());
                textViewCounty.setText("请选择");
                //根据城市的id,从数据库中查询城市列表
                retrieveCountiesWith(city.getId());
                // 清空子级数据
                counties = null;
                countyAdapter.notifyDataSetChanged();
                // 更新已选中项
                this.cityIndex = position;
                this.countyIndex = INDEX_INVALID;
                // 更新选中效果
                cityAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_COUNTY:
                ProvinceBean county = countyAdapter.getItem(position);
                textViewCounty.setText(county.getAreaname());

                this.countyIndex = position;
                countyAdapter.notifyDataSetChanged();

                callbackInternal();
                break;
        }
    }


    /**
     * 查询省份列表
     */
    private void retrieveProvinces() {
        progressBar.setVisibility(View.VISIBLE);
        queryBean = new ProvinceBean().getJsonGroup(context);
        queryProvince(queryBean);
        handler.sendMessage(Message.obtain(handler, WHAT_PROVINCES_PROVIDED, privinceList));
    }

    /**
     * 根据省份id查询城市列表
     *
     * @param provinceId 省份id
     */
    private void retrieveCitiesWith(int provinceId) {
        progressBar.setVisibility(View.VISIBLE);
        queryCitys(provinceId);
        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    /**
     * 根据城市id查询乡镇列表
     *
     * @param cityId 城市id
     */
    private void retrieveCountiesWith(int cityId) {
        progressBar.setVisibility(View.VISIBLE);
        queryAreas(cityId);
        handler.sendMessage(Message.obtain(handler, WHAT_COUNTIES_PROVIDED, counityList));
    }

    /**
     * 省份 城市 乡镇 街道 都选中完 后的回调
     */
    private void callbackInternal() {
        if (listener != null) {
            ProvinceBean province = provinces == null || provinceIndex == INDEX_INVALID ? null : provinces.get(provinceIndex);
            ProvinceBean city = cities == null || cityIndex == INDEX_INVALID ? null : cities.get(cityIndex);
            ProvinceBean county = counties == null || countyIndex == INDEX_INVALID ? null : counties.get(countyIndex);

            listener.onAddressSelected(province, city, county);
        }
    }

    /**
     * 更新进度条
     */
    private void updateProgressVisibility() {
        ListAdapter adapter = listView.getAdapter();
        int itemCount = adapter.getCount();
        progressBar.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 获得view
     *
     * @return
     */
    public View getView() {
        return view;
    }

    /**
     * 省份的adapter
     */
    class ProvinceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return provinces == null ? 0 : provinces.size();
        }

        @Override
        public ProvinceBean getItem(int position) {
            return provinces.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            ProvinceBean item = getItem(position);
            holder.textView.setText(item.getAreaname());

            boolean checked = provinceIndex != INDEX_INVALID && provinces.get(provinceIndex).getId() == item.getId();
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 城市的adaoter
     */
    class CityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cities == null ? 0 : cities.size();
        }

        @Override
        public ProvinceBean getItem(int position) {
            return cities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            ProvinceBean item = getItem(position);
            holder.textView.setText(item.getAreaname());

            boolean checked = cityIndex != INDEX_INVALID && cities.get(cityIndex).getId() == item.getId();
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 乡镇的adapter
     */
    class CountyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return counties == null ? 0 : counties.size();
        }

        @Override
        public ProvinceBean getItem(int position) {
            return counties.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            ProvinceBean item = getItem(position);
            holder.textView.setText(item.getAreaname());

            boolean checked = countyIndex != INDEX_INVALID && counties.get(countyIndex).getId() == item.getId();
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    public OnAddressSelectedListener getOnAddressSelectedListener() {
        return listener;
    }

    /**
     * 设置地址监听
     *
     * @param listener
     */
    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnDialogCloseListener {
        void dialogclose();
    }

    public void setOnDialogCloseListener(OnDialogCloseListener listener) {
        this.dialogCloseListener = listener;
    }

    private void queryProvince(final List<ProvinceBean> queryBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                privinceList.clear();
                for (ProvinceBean dataBean : queryBean) {
                    if (dataBean.getParentid() == -1) {
                        privinceList.add(dataBean);
                    }
                }
            }
        }).start();
    }

    private void queryCitys(final int parentid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cityList.clear();
                for (ProvinceBean dataBean : queryBean) {
                    if (dataBean.getParentid() == parentid) {
                        cityList.add(dataBean);
                    }
                }
            }
        }).start();
    }

    private void queryAreas(final int parentid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                counityList.clear();
                for (ProvinceBean dataBean : queryBean) {
                    if (dataBean.getParentid() == parentid) {
                        counityList.add(dataBean);
                    }
                }
            }
        }).start();
    }

}
