package top.itmp.jiandan2.cache;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;
import top.itmp.greendao.FreshNewsCacheDao;
import top.itmp.jiandan2.base.TopApplication;
import top.itmp.jiandan2.model.FreshNews;

/**
 * Created by hz on 2016/5/29.
 */
public class FreshNewsCache extends BaseCache {
    private static FreshNewsCache instance;
    private static FreshNewsCacheDao mFreshNewsCacheDao;

    private FreshNewsCache() {
    }

    public static FreshNewsCache getInstance() {
        if (instance == null) {
            synchronized (FreshNewsCache.class) {
                if (instance == null) {
                    instance = new FreshNewsCache();
                }
            }
            mDaoSession = TopApplication.getDaoSession();
            mFreshNewsCacheDao = mDaoSession.getFreshNewsCacheDao();
        }
        return instance;
    }

    public void clearAllCache() {
        mFreshNewsCacheDao.deleteAll();
    }

    @Override
    public ArrayList getCacheByPage(int page) {
        QueryBuilder<top.itmp.greendao.FreshNewsCache> query = mFreshNewsCacheDao.queryBuilder().where(FreshNewsCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) {
            try {
                return FreshNews.parseCache(new JSONArray(query.list().get(0)
                        .getResult()));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void addResultToCache(String result, int page) {
        top.itmp.greendao.FreshNewsCache freshNewsCache = new top.itmp.greendao.FreshNewsCache();
        freshNewsCache.setResult(result);
        freshNewsCache.setPage(page);
        freshNewsCache.setTime(System.currentTimeMillis());

        mFreshNewsCacheDao.insert(freshNewsCache);
    }
}
