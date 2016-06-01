package top.itmp.jiandan2.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by hz on 2016/5/29.
 */
public class CustomFields implements Serializable {

    private String thumb_c;
    private String thumb_m;
    private String views;

    public static CustomFields parse(final JSONObject jsonObject) {
        CustomFields customFields;

        if (jsonObject == null) {
            customFields = null;
        } else {
            customFields = new CustomFields();
            final JSONArray jsonArray = jsonObject.optJSONArray("thumb_c");
            if (jsonArray != null && jsonArray.length() > 0) {
                customFields.thumb_c = jsonArray.optString(0);
                if (customFields.thumb_c.contains("custom")) {
                    customFields.thumb_m = customFields.thumb_c.replace("custom", "medium");
                }
            }

            final JSONArray jsonArray1 = jsonObject.optJSONArray("views");
            if (jsonArray1 != null && jsonArray1.length() > 0) {
                customFields.views = jsonArray1.optString(0);
            }
        }
        return customFields;
    }

    public static CustomFields parseCache(final JSONObject jsonObject) {
        CustomFields customFields;
        if (jsonObject == null) {
            customFields = null;
        } else {
            customFields = new CustomFields();
            if (jsonObject.optString("thumb_c") != null) {
                customFields.thumb_c = jsonObject.optString("thumb_c");
                if (customFields.thumb_c.contains("custom")) {
                    customFields.thumb_m = customFields.thumb_c.replace("custom", "medium");
                }
            }
            customFields.views = jsonObject.optString("views");
        }
        return customFields;
    }

    public String getThumb_m() {
        return thumb_m;
    }

    public String getViews() {
        return views;
    }
}
