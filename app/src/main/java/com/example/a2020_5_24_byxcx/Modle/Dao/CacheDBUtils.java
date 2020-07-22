package com.example.a2020_5_24_byxcx.Modle.Dao;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class CacheDBUtils {

    private static final String TAG = "dbDBUtils";

    public DaoManager mManager;

    public CacheDBUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成db记录的插入，如果表未创建，先创建db表
     *
     * @return
     */
    public boolean insertdb(CacheModle db) {
        boolean flag = false;
        flag = mManager.getDaoSession().getCacheModleDao().insertOrReplace(db)== -1 ? false : true;
        Log.i(TAG, "insert Meizi :" + flag + "-->" + db.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @return
     */
    public boolean insertMultCacheModle(final List<CacheModle> CacheModleList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (CacheModle db : CacheModleList) {
                        mManager.getDaoSession().insertOrReplace(db);
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
    public boolean updateCacheModle(CacheModle db) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(db);
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
    public boolean deleteCacheModle(CacheModle db) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(db);
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
            mManager.getDaoSession().deleteAll(CacheModle.class);
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
    public List<CacheModle> queryAllCacheModle() {
        return mManager.getDaoSession().loadAll(CacheModle.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public CacheModle queryCacheModleById(String key) {
        return mManager.getDaoSession().load(CacheModle.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<CacheModle> queryCacheModleByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(CacheModle.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<CacheModle> queryCacheModleByQueryBuilder(long id) {
        QueryBuilder<CacheModle> queryBuilder = mManager.getDaoSession().queryBuilder(CacheModle.class);
        return queryBuilder.where(CacheModleDao.Properties.Id.eq(id)).list();
    }
    public List<CacheModle> queryCacheModleByQueryBuilder_toType(String type) {
        QueryBuilder<CacheModle> queryBuilder = mManager.getDaoSession().queryBuilder(CacheModle.class);
        List<CacheModle> list = queryBuilder.where(CacheModleDao.Properties.Type.eq(type)).list();
        return list;
    }
    public List<CacheModle> queryCacheModleByQueryBuilder_toTitle(String title) {
        QueryBuilder<CacheModle> queryBuilder = mManager.getDaoSession().queryBuilder(CacheModle.class);
        List<CacheModle> list = queryBuilder.where(CacheModleDao.Properties.Title.eq(title)).list();
        return list;
    }

    public void close(){
        mManager.closeConnection();
    }
}
