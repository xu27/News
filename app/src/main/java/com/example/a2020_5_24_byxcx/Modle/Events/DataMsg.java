package com.example.a2020_5_24_byxcx.Modle.Events;

import com.example.xcxlibrary.Util.EventMessage;

public class DataMsg<T> {
    private int code;
    private T data;
    private String type;
    private int start;

    public DataMsg(int code) {
        this.code = code;
    }

    public DataMsg(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
