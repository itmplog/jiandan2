package top.itmp.jiandan2.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import top.itmp.jiandan2.base.TopApplication;

/**
 * Created by hz on 2016/5/29.
 */
public class NetworkUtils {

    public static boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) TopApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)TopApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
