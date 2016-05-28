package top.itmp.jiandan2.cache;

import java.util.ArrayList;

import top.itmp.greendao.DaoSession;

/**
 * Created by hz on 2016/5/28.
 */
public abstract class BaseCache<T> {

    public static final String DB_NAME = "jiandan-db";

    protected static DaoSession mDaoSession;

    public abstract void clearAllCache();

    public abstract ArrayList<T> getCacheByPage(int page);

    public abstract void addResultToCache(String result, int page);
}
