package top.itmp.jiandan2.net;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by hz on 2016/5/29.
 */
public class JSONParser {

    protected static Gson gson;

    public static String toString(Object obj) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(obj);
    }

    public static Object toObject(String jsonString, Object type) {
        if (gson == null) {
            gson = new Gson();
        }

        jsonString = jsonString.replace("&nbsp", "");
        jsonString = jsonString.replace("﹠nbsp", "");
        jsonString = jsonString.replace("nbsp", "");
        jsonString = jsonString.replace("&amp;", "");
        jsonString = jsonString.replace("&amp", "");
        jsonString = jsonString.replace("amp", "");

        if (type instanceof Type) {
            return gson.fromJson(jsonString, (Type) type);
        } else if (type instanceof Class<?>) {
            return gson.fromJson(jsonString, (Class<?>) type);
        }else{
            throw new RuntimeException("只能是Class<?>或者通过TypeToken获取的Type类型");
        }
    }


}
