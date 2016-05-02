package top.itmp.jiandan2.base;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by hz on 2016/4/5.
 */
public class TopApplication extends Application {
    private static Context mContext;
    private RefWatcher mRefWatcher;

    public static Context getContext() {
        return mContext;
    }

    public static RefWatcher getRefWatcher(Context context) {
        TopApplication application = (TopApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                //.instanceField("android.support.v7.widget.RecyclerView", "mContext")
                // inv
                .staticField("android.view.inputmethod.InputMethodManager", "sInstance")
                .instanceField("android.support.v7.widget.RecyclerView", "mContext")
                .instanceField("top.itmp.uidemo.ui.MainActivity", "instance")
                .reason("recyclerView leak")
                .build();
        mRefWatcher = LeakCanary.install(this, DisplayLeakService.class, excludedRefs);
    }

}
