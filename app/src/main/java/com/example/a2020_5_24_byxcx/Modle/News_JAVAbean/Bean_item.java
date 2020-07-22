package com.example.a2020_5_24_byxcx.Modle.News_JAVAbean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Bean_item implements Parcelable {
    protected String source;
    protected String title;
    protected String url;
    protected String digest;
    protected String imgsrc;
    protected String ptime;
    protected String docid;

    protected Bean_item(Parcel in) {
        source = in.readString();
        title = in.readString();
        url = in.readString();
        digest = in.readString();
        imgsrc = in.readString();
        ptime = in.readString();
        docid = in.readString();
    }

    public Bean_item() {
    }

    public Bean_item(String source, String title, String url, String digest, String imgsrc, String ptime, String docid) {
        this.source = source;
        this.title = title;
        this.url = url;
        this.digest = digest;
        this.imgsrc = imgsrc;
        this.ptime = ptime;
        this.docid = docid;
    }

    public static final Creator<Bean_item> CREATOR = new Creator<Bean_item>() {
        @Override
        public Bean_item createFromParcel(Parcel in) {
            return new Bean_item(in);
        }

        @Override
        public Bean_item[] newArray(int size) {
            return new Bean_item[size];
        }
    };

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    @NonNull
    @Override
    public String toString() {
        return source + "," + title + "," + digest + "," + url + "," + imgsrc + "," + ptime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(source);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(digest);
        parcel.writeString(imgsrc);
        parcel.writeString(ptime);
        parcel.writeString(docid);
    }
}
