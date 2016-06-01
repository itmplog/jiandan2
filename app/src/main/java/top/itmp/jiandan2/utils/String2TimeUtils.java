package top.itmp.jiandan2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hz on 2016/5/29.
 * 用于将String转化为时间
 * Created by zhaokaiqiang on 15/4/9.
 */
public class String2TimeUtils {


    /**
     * 转换日期格式到用户体验好的时间格式
     *
     * @param time 2015-04-11 12:45:06
     * @return
     */
    public static String dateString2GoodExperienceFormat(String time) {
        if (isNullString(time)) {
            return "";
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            try {
                String timeString;
                Date parse = simpleDateFormat.parse(time);
                long distanceTime = new Date().getTime() - parse.getTime();
                if (distanceTime < 0L) {
                    timeString = "0 mins ago";
                } else {
                    long n2 = distanceTime / 60000L;
                    new SimpleDateFormat("HH:mm");
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd HH:mm");
                    if (n2 < 60L) {
                        timeString = String.valueOf(n2) + "mins ago";
                    } else if (n2 < 720L) {
                        timeString = String.valueOf(n2 / 60L) + "hours ago";
                    } else {
                        timeString = simpleDateFormat2.format(parse);
                    }
                }
                return timeString;
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        }
    }

    public static boolean isNullString(String s) {
        return s == null || s.equals("") || s.equals("null");
    }


}


