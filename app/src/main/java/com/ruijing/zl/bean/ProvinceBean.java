package com.ruijing.zl.bean;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProvinceBean {
    private int id;
    private String areaname;
    private int level;
    private int parentid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public static List<ProvinceBean> getJsonGroup(Context context) {
        List<ProvinceBean> provinceBeans = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open("city.json");
            String content = IOUtil.toString(inputStream);

            provinceBeans = new Gson().fromJson(content, new TypeToken<List<ProvinceBean>>() {
            }.getType());
            return provinceBeans;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
