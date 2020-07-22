package com.example.a2020_5_24_byxcx.Base;

import android.database.sqlite.SQLiteDatabase;


import com.example.a2020_5_24_byxcx.Modle.Dao.DaoManager;
import com.example.a2020_5_24_byxcx.Modle.Dao.DaoMaster;
import com.example.a2020_5_24_byxcx.Modle.Dao.DaoSession;
import com.example.xcxlibrary.BaseApp;

public class MyBaseApp extends BaseApp {

    public static DaoSession mSession;

    @Override
    protected boolean isInitializedGreenDao() {
        return true;
    }

    @Override
    protected boolean isInitializedOkHttp() {
        return true;
    }

    @Override
    protected void setgreenDao() {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }
}
