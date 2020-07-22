package com.example.xcxlibrary.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyComment {

    /**
     * againstLock : 0
     * audioLock : 1
     * code : 1
     * docUrl : https://news.163.com/20/0604/16/FE9RC0UM0001875O.html
     * hotPosts : [{"1":{"a":"0","b":"回答的很棒","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易新疆博尔塔拉蒙古自治州手机网友&nbsp;AIQIAO： ","fi":"0","ip":"110.155.*.*","l":"0","n":"AIQIAO","p":"129491898","pi":"FE9RC0UM0001875O_129491898","rp":"0","source":"ph","t":"2020-06-04 19:21:40","timg":"http://mobilepics.ws.126.net/87c8a2c0adeb64901ccea6064a7697030178.jpg","u":"3PuIuNwT6Mtr2WuANlTtNYiDezRSaOGYQ%2BTcb0tuSocQhrevOX3GNE4PCuMQ9jdfiMjOTGkrNwylMSjrS1z91w%3D%3D","v":"113"}},{"1":{"a":"0","b":"中国历来来去自由。","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易江苏徐州手机网友&nbsp;叶障目： ","fi":"0","ip":"223.104.*.*","l":"0","n":"叶障目","p":"129594107","pi":"FE9RC0UM0001875O_129594107","rp":"0","source":"ph","t":"2020-06-04 22:40:39","timg":"http://cms-bucket.ws.126.net/2020/0309/1efd6c96j00q6wwg60001c200140014m00690069.jpg","u":"OwaecchUlSNYhgzuCVZu1G00xNiAeP4I%2F31aBnteOPfSXMkIPLXamKb8GyolYshzf%2BBz7mXlZ2CiV8ZCFLX1Cg%3D%3D","v":"64"}},{"1":{"a":"0","b":"不爱国的人渣也没必要留","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易江苏苏州网友&nbsp;有态度网友001rYf： ","fi":"0","ip":"117.62.*.*","l":"0","n":"有态度网友001rYf","p":"129489283","pi":"FE9RC0UM0001875O_129489283","rp":"0","source":"wb","t":"2020-06-04 19:13:02","timg":"","u":"UaESjVd6YknIS7SOEqN9xw%3D%3D","v":"63"}},{"1":{"a":"0","b":"想走的，赶紧走吧！","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","ext":{"GPSInfo":"28.113384#113.034575"},"f":"网易湖南长沙手机网友：","fi":"0","ip":"119.39.*.*","l":"0","p":"129499175","pi":"FE9RC0UM0001875O_129499175","rp":"0","source":"ph","t":"2020-06-04 19:31:45","u":"B2JffYIlJcdCIkyipYgXKFNJ%2BUlNBKYOCZhT8FrBoyVwwqjAfzlN3hniH2sVW0fZiMjOTGkrNwylMSjrS1z91w%3D%3D","v":"56"}},{"1":{"a":"0","authInfo":"网易号电影内容作者","b":"我们负责出机票送走这些废物","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","ext":{"GPSInfo":"23.423#113.179"},"f":"网易广东广州手机网友&nbsp;只关于电影： ","fi":"0","ip":"2408:84f3:164f:82b5:893d:c414:*:*","l":"0","n":"只关于电影","p":"129721753","pi":"FE9RC0UM0001875O_129721753","rp":"0","source":"ph","t":"2020-06-05 07:26:47","timg":"http://dingyue.ws.126.net/2020/0421/df36fe95j00q94hvm000bc0004g004gc.jpg","u":"trtwUcoaOaYNwIqQ69sd2rkYZbbJKrPZXkqX8io36BE%3D","v":"49","vip":"vipw"}},{"1":{"a":"0","b":"赵立坚底气十足！","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","ext":{"GPSInfo":"23.095#113.292"},"f":"网易手机网友&nbsp;有态度网友0gvZ-Q： ","fi":"0","ip":"unknown","l":"0","n":"有态度网友0gvZ-Q","p":"129586214","pi":"FE9RC0UM0001875O_129586214","rp":"0","source":"ph","t":"2020-06-04 22:25:30","timg":"","u":"qOPzNXftjKBMw0%2B0bsWU3uJfUucyUo8HO7kXSv3xWXc%3D","v":"41"}},{"1":{"a":"0","b":"来去自由","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易天津手机网友&nbsp;格兰宁根： ","fi":"0","ip":"125.38.*.*","l":"0","n":"格兰宁根","p":"129551285","pi":"FE9RC0UM0001875O_129551285","rp":"0","source":"ph","t":"2020-06-04 21:19:25","timg":"http://mobilepics.ws.126.net/7f75549738f8b4456b73aa0ed3ec331e0199.jpg","u":"OnyRld%2FdHTb%2F8BHyrnPdis6uHigl3mf%2BXc9Cb2RnsjIL4LzIMTooFCSM6Fr2LrjXiMjOTGkrNwylMSjrS1z91w%3D%3D","v":"41"}},{"1":{"a":"0","b":"来去自由，好走不送。","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易重庆网友&nbsp;菜羊菜羊： ","fi":"0","ip":"125.85.*.*","l":"0","n":"菜羊菜羊","p":"129527588","pi":"FE9RC0UM0001875O_129527588","rp":"0","source":"wb","t":"2020-06-04 20:32:55","timg":"","u":"905Hly%2B8kxClYtcGa2EVzLkYZbbJKrPZXkqX8io36BE%3D","v":"40"}},{"1":{"a":"0","b":"回答的太给力了","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","ext":{"GPSInfo":"32.943033#117.392769"},"f":"网易安徽蚌埠手机网友&nbsp;宝宝贝贝嗨起来： ","fi":"0","ip":"120.243.*.*","l":"0","n":"宝宝贝贝嗨起来","p":"129588905","pi":"FE9RC0UM0001875O_129588905","rp":"0","source":"ph","t":"2020-06-04 22:34:18","timg":"http://mobilepics.ws.126.net/64e5a6f022de079ca7fb3484736c4e320209.jpg","u":"zAoejmx%2Bv02KtWeLjyqgV%2BEbwdlXJXVmkWad7ondmoc%3D","v":"39"}},{"1":{"a":"0","b":"回答的有力","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易黑龙江哈尔滨网友：","fi":"0","ip":"112.103.*.*","l":"0","p":"129516479","pi":"FE9RC0UM0001875O_129516479","rp":"0","source":"wb","t":"2020-06-04 20:10:23","u":"pX3hzaH7IE7jrLryBYxyDYjIzkxpKzcMpTEo60tc%2Fdc%3D","v":"38"}}]
     * isTagOff : 0
     */

    private String againstLock;
    private String audioLock;
    private int code;
    private String docUrl;
    private String isTagOff;
    private List<HotPostsBean> hotPosts;

    public String getAgainstLock() {
        return againstLock;
    }

    public void setAgainstLock(String againstLock) {
        this.againstLock = againstLock;
    }

    public String getAudioLock() {
        return audioLock;
    }

    public void setAudioLock(String audioLock) {
        this.audioLock = audioLock;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getIsTagOff() {
        return isTagOff;
    }

    public void setIsTagOff(String isTagOff) {
        this.isTagOff = isTagOff;
    }

    public List<HotPostsBean> getHotPosts() {
        return hotPosts;
    }

    public void setHotPosts(List<HotPostsBean> hotPosts) {
        this.hotPosts = hotPosts;
    }

    public static class HotPostsBean {
        /**
         * 1 : {"a":"0","b":"回答的很棒","bi":"news_guoji2_bbs","d":"FE9RC0UM0001875O","f":"网易新疆博尔塔拉蒙古自治州手机网友&nbsp;AIQIAO： ","fi":"0","ip":"110.155.*.*","l":"0","n":"AIQIAO","p":"129491898","pi":"FE9RC0UM0001875O_129491898","rp":"0","source":"ph","t":"2020-06-04 19:21:40","timg":"http://mobilepics.ws.126.net/87c8a2c0adeb64901ccea6064a7697030178.jpg","u":"3PuIuNwT6Mtr2WuANlTtNYiDezRSaOGYQ%2BTcb0tuSocQhrevOX3GNE4PCuMQ9jdfiMjOTGkrNwylMSjrS1z91w%3D%3D","v":"113"}
         */

        @SerializedName("1")
        private _$1Bean _$1;

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public static class _$1Bean {
            /**
             * a : 0
             * b : 回答的很棒
             * bi : news_guoji2_bbs
             * d : FE9RC0UM0001875O
             * f : 网易新疆博尔塔拉蒙古自治州手机网友&nbsp;AIQIAO：
             * fi : 0
             * ip : 110.155.*.*
             * l : 0
             * n : AIQIAO
             * p : 129491898
             * pi : FE9RC0UM0001875O_129491898
             * rp : 0
             * source : ph
             * t : 2020-06-04 19:21:40
             * timg : http://mobilepics.ws.126.net/87c8a2c0adeb64901ccea6064a7697030178.jpg
             * u : 3PuIuNwT6Mtr2WuANlTtNYiDezRSaOGYQ%2BTcb0tuSocQhrevOX3GNE4PCuMQ9jdfiMjOTGkrNwylMSjrS1z91w%3D%3D
             * v : 113
             */

            private String a;
            private String b;
            private String bi;
            private String d;
            private String f;
            private String fi;
            private String ip;
            private String l;
            private String n;
            private String p;
            private String pi;
            private String rp;
            private String source;
            private String t;
            private String timg;
            private String u;
            private String v;

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getB() {
                return b;
            }

            public void setB(String b) {
                this.b = b;
            }

            public String getBi() {
                return bi;
            }

            public void setBi(String bi) {
                this.bi = bi;
            }

            public String getD() {
                return d;
            }

            public void setD(String d) {
                this.d = d;
            }

            public String getF() {
                return f;
            }

            public void setF(String f) {
                this.f = f;
            }

            public String getFi() {
                return fi;
            }

            public void setFi(String fi) {
                this.fi = fi;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getL() {
                return l;
            }

            public void setL(String l) {
                this.l = l;
            }

            public String getN() {
                return n;
            }

            public void setN(String n) {
                this.n = n;
            }

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }

            public String getPi() {
                return pi;
            }

            public void setPi(String pi) {
                this.pi = pi;
            }

            public String getRp() {
                return rp;
            }

            public void setRp(String rp) {
                this.rp = rp;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }

            public String getTimg() {
                return timg;
            }

            public void setTimg(String timg) {
                this.timg = timg;
            }

            public String getU() {
                return u;
            }

            public void setU(String u) {
                this.u = u;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }
        }
    }
}
