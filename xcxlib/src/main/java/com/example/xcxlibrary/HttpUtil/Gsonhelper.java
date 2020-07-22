package com.example.xcxlibrary.Util.HttpUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Gsonhelper {

    /**
     *
     * @param jsondata 传入json数据
     * @param javabean 传入对应的javabean
     * @param <T> 泛型
     * @return
     */
    public static<T> T getJsonBean(String jsondata,Class<T> javabean){
        T t = null;
        Gson gson=new Gson();
        t = gson.fromJson(jsondata,javabean);
        return t;
    }

    /**
     * @param type 对应TypeToken
     * @param jsondata json数据
     * @param <T> //回传一个泛型数组
     * @return
     */
    public static<T> List<T> getJsonBeans(String jsondata,TypeToken<List<T>> type){
        List<T> tList = null;
        Gson gson = new Gson();
        tList = gson.fromJson(jsondata,type.getType());
        return tList;
    }
}
