package top.itmp.jiandan2.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by hz on 2016/5/29.
 */
public class Tags implements Serializable {

    private int id;
    private String title;
    private String description;

    public static Tags parse(final JSONArray jsonArray){
        Tags tags;
        if(jsonArray == null){
            tags = null;
        }else{
            tags = new Tags();
            JSONObject jsonObject = jsonArray.optJSONObject(0);
            if(jsonObject != null){
                tags.id = jsonObject.optInt("id");
                tags.title = jsonObject.optString("title");
                tags.description = jsonObject.optString("description");
            }
        }
        return tags;
    }

    public static Tags parseCache(final JSONObject jsonObject){
        Tags tags;
        if(jsonObject == null){
            tags = null;
        }else{
            tags = new Tags();
            tags.id = jsonObject.optInt("int");
            tags.title = jsonObject.optString("title");
            tags.description = jsonObject.optString("description");
        }
        return tags;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
