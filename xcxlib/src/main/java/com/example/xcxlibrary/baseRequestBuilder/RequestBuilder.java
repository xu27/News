package com.example.xcxlibrary.baseRequestBuilder;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;

public abstract class RequestBuilder {

    protected String url;
    protected Map<String,String> params;
    protected Object tag;

    public abstract Call enqueue(Callback callback);

    protected void addparams(FormBody.Builder builder,Map<String,String> params){
        if (builder!=null & params!=null){
            for (String key:params.keySet()){
                builder.add(key,params.get(key));
            }
        }
    }

    /**
     * 这个是复制的
     * @param url
     * @param params
     * @return
     */
    protected String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
