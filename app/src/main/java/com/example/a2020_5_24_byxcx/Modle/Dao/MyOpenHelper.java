package com.example.a2020_5_24_byxcx.Modle.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        // 迁移数据库(如果修改了多个实体类，则需要把对应的Dao都传进来)
        //MigrationHelper.migrate(db, NewsModle.class);
    }
}
