package com.example.xcxlibrary.parser;

import com.example.xcxlibrary.Util.HttpUtil.Gsonhelper;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class GsonPraser<T> extends BaseParser {

    private Class<T> javaBean = null;
    private TypeToken<List<T>> type = null;

    public GsonPraser(Class<T> javaBean) {
        this.javaBean = javaBean;
    }

    public GsonPraser(TypeToken<List<T>> type) {
        this.type = type;
    }

    @Override
    public Object parse(Response response) throws IOException {
        String data = null;
        if (response.isSuccessful()) {
            data = response.body().string();
            return (javaBean != null & type != null)?getGsonBean(javaBean,data):getGsonBeans(type,data);
        }
        return null;
    }

    private List<T> getGsonBeans(TypeToken<List<T>> type, String data) {
        List<T> list = null;
        list = Gsonhelper.getJsonBeans(data,type);
        return list;
    }

    private T getGsonBean(Class<T> javaBean,String data) {
        T t=null;
        t=Gsonhelper.getJsonBean(data,javaBean);
        return t;
    }

}
