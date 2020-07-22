package com.example.a2020_5_24_byxcx.Modle.News_JAVAbean;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有新闻bean的基类
 * 便于识别类型
 */
public class Base_Bean {

    private String type;
    private int start;
    private List<Bean_item> itemList;

    public Base_Bean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Bean_item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Bean_item> itemList) {
        this.itemList = itemList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

}
