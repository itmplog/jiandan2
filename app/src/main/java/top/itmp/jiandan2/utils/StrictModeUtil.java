package top.itmp.jiandan2.utils;

import android.os.Build;
import android.os.StrictMode;

import top.itmp.jiandan2.BuildConfig;

/**
 * Created by hz on 2016/5/28.
 * 开启严格模式， 检测内存， 硬盘等敏感操作， 线程监控出现问题会出现对话框提示
 */
public class StrictModeUtil {

    private static boolean isShow = true;

    public static void init() {
        if (isShow && BuildConfig.DEBUG && Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            // 线程监控，会弹框
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog().build());

            //vm监控
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }
}
