package top.itmp.jiandan2.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.Random;

import top.itmp.jiandan2.R;

/**
 * Created by hz on 2016/6/1.
 */
public class ShareUtils {

    public static void shareImage(Activity activity, String url) {
        String[] urlSplits = url.split("\\.");

        File cacheFile = ImageLoadProxy.getImageLoader().getDiskCache().get(url);

        if (!cacheFile.exists()) {
            String picUrl = url;
            picUrl = picUrl.replace("mw600", "small").replace("mw1200", "small")
                    .replace("large", "small");
            cacheFile = ImageLoadProxy.getImageLoader().getDiskCache().get(picUrl);
        }

        File newFile = new File(cacheFile.getAbsolutePath() + new Random().nextInt(100000) + "." + urlSplits[urlSplits.length - 1]);

        if (FileUtils.copyTo(cacheFile, newFile)) {
            ShareUtils.shareImage(activity, newFile.getAbsolutePath(),
                    "分享自煎蛋 " + url);
        } else {
            UI.ShortToast("分享失败!");
        }
    }

    public static void shareImage(Activity activity, String imgPath, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        File f = new File(imgPath);
        if (f != null && f.exists() && f.isFile()) {
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        } else {
            UI.ShortToast("分享图片不存在哦");
            return;
        }

        //GIF图片指明出处url，其他图片指向项目地址
        if (imgPath.endsWith(".gif")) {
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }

    public static void shareText(Activity activity, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }
}

