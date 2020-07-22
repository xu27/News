package com.example.a2020_5_24_byxcx.Modle.Dao;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class NewsDBUtils {

    private static final String TAG = "NewsDBUtils";

    public DaoManager mManager;

    public NewsDBUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成news记录的插入，如果表未创建，先创建news表
     *
     * @return
     */
    public boolean insertNews(NewsModle news) {
        boolean flag = false;
        flag = mManager.getDaoSession().getNewsModleDao().insert(news) == -1 ? false : true;
        Log.i(TAG, "insert Meizi :" + flag + "-->" + news.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @return
     */
    public boolean insertMultNewsModle(final List<NewsModle> newsModleList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (NewsModle news : newsModleList) {
                        mManager.getDaoSession().insertOrReplace(news);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @return
     */
    public boolean updateNewsModle(NewsModle news) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(news);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录\
     *
     * @return
     */
    public boolean deleteNewsModle(NewsModle news) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(news);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(NewsModle.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<NewsModle> queryAllNewsModle() {
        return mManager.getDaoSession().loadAll(NewsModle.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public NewsModle queryNewsModleById(String key) {
        return mManager.getDaoSession().load(NewsModle.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<NewsModle> queryNewsModleByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(NewsModle.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<NewsModle> queryNewsModleByQueryBuilder(long id) {
        QueryBuilder<NewsModle> queryBuilder = mManager.getDaoSession().queryBuilder(NewsModle.class);
        return queryBuilder.where(NewsModleDao.Properties.Id.eq(id)).list();
    }

    public void close(){
        mManager.closeConnection();
    }
}
