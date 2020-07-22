package com.example.a2020_5_24_byxcx.Modle.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class NewsModle {
    @Id
    private String id;
    private String url;
    private String img;
    private String title;
    private String source;

    @Keep
    public NewsModle(String id, String url, String img, String title, String source) {
        this.id = id;
        this.url = url;
        this.img = img;
        this.title = title;
        this.source = source;
    }

    @Generated(hash = 1589460761)
    public NewsModle() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
