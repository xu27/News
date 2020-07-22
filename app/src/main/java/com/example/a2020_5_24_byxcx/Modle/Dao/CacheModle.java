package com.example.a2020_5_24_byxcx.Modle.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public
class CacheModle {
    @Id
    private String id;
    private String source;
    private String title;
    private String url;
    private String digest;
    private String imgsrc;
    private String ptime;
    private String docid;
    private String html;
    private String type;
    @Generated(hash = 1986058475)
    public CacheModle(String id, String source, String title, String url,
            String digest, String imgsrc, String ptime, String docid, String html,
            String type) {
        this.id = id;
        this.source = source;
        this.title = title;
        this.url = url;
        this.digest = digest;
        this.imgsrc = imgsrc;
        this.ptime = ptime;
        this.docid = docid;
        this.html = html;
        this.type = type;
    }
    @Generated(hash = 408714645)
    public CacheModle() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDigest() {
        return this.digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getImgsrc() {
        return this.imgsrc;
    }
    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
    public String getPtime() {
        return this.ptime;
    }
    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
    public String getDocid() {
        return this.docid;
    }
    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getHtml() {
        return this.html;
    }
    public void setHtml(String html) {
        this.html = html;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
   
}
