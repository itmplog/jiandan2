package top.itmp.jiandan2.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.text.DecimalFormat;

import top.itmp.jiandan2.R;
import top.itmp.jiandan2.utils.FileUtils;
import top.itmp.jiandan2.utils.ImageLoadProxy;
import top.itmp.jiandan2.utils.UI;

/**
 * Created by hz on 2016/6/2.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final String CLEAR_CACHE = "clear_cache";
    public static final String ABOUT = "about";
    public static final String VERSION = "version";
    public static final String ENABLE_SISTER = "enable_sister";
    public static final String ENABLE_BIG = "enable_big";

    private Preference clearCache;
    private Preference about;
    private Preference version;
    private CheckBoxPreference enableSister;
    private CheckBoxPreference enableBig;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        clearCache = findPreference(CLEAR_CACHE);
        about = findPreference(ABOUT);
        version = findPreference(VERSION);
        enableSister = (CheckBoxPreference) findPreference(ENABLE_SISTER);
        enableBig = (CheckBoxPreference) findPreference(ENABLE_BIG);

        version.setTitle(getVersionName(getActivity()));

        File cacheFile = ImageLoadProxy.getImageLoader().getDiskCache().getDirectory();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        clearCache.setSummary("缓存大小: " + decimalFormat.format(FileUtils.getDirSize(cacheFile)));

        enableSister.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                UI.ShortToast(((Boolean)newValue) ? "已起用妹子图" : "已关闭妹子图");
                return true;
            }
        });

        enableBig.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                UI.ShortToast(((Boolean)newValue) ? "已开启大图模式" : "已关闭大图模式");
                return true;
            }
        });

        clearCache.setOnPreferenceClickListener(onPreferenceClickListener);
        about.setOnPreferenceClickListener(onPreferenceClickListener);
    }

    Preference.OnPreferenceClickListener onPreferenceClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()){
                case CLEAR_CACHE:
                    ImageLoadProxy.getImageLoader().clearDiskCache();
                    UI.ShortToast("清除缓存成功");
                    clearCache.setSummary("缓存大小: 0.00M");
                    break;
                case ABOUT:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("煎蛋2开源版")
                            .setMessage("煎蛋主题是「新鲜事」，每天从Digg/Mashable/Slashdot/Reddit/Gawker/LiveScience等Blog Media取材，挑选最好玩的内容以译介方式分享给读者.")
                            .setPositiveButton("Github", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nullog/jiandan2")));
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Weibo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://weibo.com/huaixiaoz")));
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
            }
            return true;
        }
    };

    private String getVersionName(Activity activity){
        PackageManager packageManager = activity.getPackageManager();
        PackageInfo packageInfo = null;
        try{
            packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            String version = packageInfo.versionName;
            return version;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "0";
        }
    }

}
