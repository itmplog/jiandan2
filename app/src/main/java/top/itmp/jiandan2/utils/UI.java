package top.itmp.jiandan2.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.lang.reflect.Field;

import top.itmp.jiandan2.base.TopApplication;

/**
 * Created by hz on 2016/4/27.
 */
public class UI {

    public static int getStatusBarHeight(Context context) {

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 38;//通常这个值会是38

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return statusBarHeight;

    }

    public static int getStatusBarHeight(Resources resources){
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0){
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationHeight(Context context, int orientation){
        Resources resources = context.getResources();

        int id = resources.getIdentifier(
                orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape",
                "dimen", "android");
        if (id > 0) {
            return resources.getDimensionPixelSize(id);
        }
        return 0;
    }

    public static void ShortToast(@NonNull CharSequence sequence) {
        Toast.makeText(TopApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

    public static void LongToast(@NonNull CharSequence sequence) {
        Toast.makeText(TopApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

}
