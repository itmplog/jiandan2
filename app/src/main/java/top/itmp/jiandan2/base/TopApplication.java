package top.itmp.jiandan2.base;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import top.itmp.greendao.DaoMaster;
import top.itmp.greendao.DaoSession;
import top.itmp.jiandan2.cache.BaseCache;
import top.itmp.jiandan2.utils.ImageLoadProxy;
import top.itmp.jiandan2.utils.StrictModeUtil;

/**
 * Created by hz on 2016/4/5.
 */
public class TopApplication extends Application {
    private static Context mContext;
    private RefWatcher mRefWatcher;

    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        //StrictModeUtil.init(); //StrictMode 线程监控， VM监控
        super.onCreate();

        mContext = this;
        ImageLoadProxy.initImageLoader(this);

        mRefWatcher = LeakCanary.install(this);
    }

    public static Context getContext(){
        return mContext;
    }

    public static RefWatcher getRefWatcher(Context context) {
        TopApplication application = (TopApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public static DaoMaster getDaoMaster(){
        if(mDaoMaster == null){
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, BaseCache.DB_NAME, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    public static DaoSession getDaoSession(){
        if(mDaoSession == null){
            if(mDaoMaster == null){
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    public static RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }
}
