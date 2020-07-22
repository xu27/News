package com.example.xcxlibrary.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqlManager extends SQLiteOpenHelper {

    private static String db_name = "TEST.db";
    private static int db_version = 1;
    private String createtab = "create table test_tb (user_id integer primary key autoincrement," +
            "name varchar(20)," +
            "age integer)";
    private String TAG = "SqlManager:";
    private String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "uname text,"
            + "upassword text," +
            "uemail text)";


    private Context mContext;
    private static SqlManager sInstance;

    public static synchronized SqlManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SqlManager.class) {
                if (sInstance == null) {
                    sInstance = new SqlManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }


    public SqlManager(Context context) {
        super(context, context.getExternalFilesDir("db").getPath()+"/"+ db_name, null, db_version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createtab);
        //创建成功后提示
        Toast.makeText(mContext, "数据库和数据库表创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * @param tabname       数据库表名
     * @param contentValues 数据库传参键值
     * @return 是否成功
     */
    public boolean insertinTab(String tabname, ContentValues contentValues) {
        //返回主键id
        SQLiteDatabase db = sInstance.getReadableDatabase();
        Long id = db.insert(tabname, null, contentValues);
        db.close();
        if (id != -1) {
            return true;
        }
        return false;
    }

    /**
     * 仅适合单项可为空的表
     *
     * @param tabname
     * @param nullid        可为空的列名
     * @param contentValues
     * @return
     */
    public boolean insertinTab(String tabname, String nullid, ContentValues contentValues) {
        //返回主键id
        SQLiteDatabase db = sInstance.getReadableDatabase();
        Long id = db.insert(tabname, nullid, contentValues);
        db.close();
        if (id != -1) {
            return true;
        }
        return false;
    }

    /**
     * @param tabname
     * @param valuename 例： id = ?
     * @param values    // id 对应值
     * @return
     */
    public int deletinTab(String tabname, String valuename, String[] values) {
        SQLiteDatabase db = sInstance.getReadableDatabase();
        int cuont = db.delete(tabname, valuename, values);
        db.close();
        return cuont;
    }

    /**
     * @param tabname        表名
     * @param linename       列名
     * @param condition      插入条件  xx = ?
     * @param conditionvalue ?的值
     * @param groupby        分组 传入列名
     * @param having         条件 xx = xx
     * @param order          排序条件 传入列名
     * @return
     */
    public Cursor queryinTab(String tabname, String[] linename, String condition, String[] conditionvalue, String groupby, String having, String order) {
        SQLiteDatabase db = sInstance.getReadableDatabase();
        Cursor cursor = db.query(tabname, linename, condition, conditionvalue, groupby, having, order);
        return cursor;
    }

    public int updatainTab(String tabname, ContentValues values, String condition, String[] conditionvalue) {
        SQLiteDatabase db = sInstance.getReadableDatabase();
        int i = db.update(tabname, values, condition, conditionvalue);
        db.close();
        return i;
    }


    @Override
    public synchronized void close() {
        super.close();
    }
}
