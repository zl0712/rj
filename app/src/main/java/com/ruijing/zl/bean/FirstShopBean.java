package com.ruijing.zl.bean;

import java.util.List;

public class FirstShopBean {
    private String name;
    private List<Shop> list;

    public List<Shop> getList() {
        return list;
    }

    public void setList(List<Shop> list) {
        this.list = list;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Shop {
        private String url;
        private int price;
        private String name;
        private String content;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
