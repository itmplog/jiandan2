package top.itmp.jiandan2.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hz on 2016/5/29.
 */
public class FreshNews implements Serializable {

    public static final String URL_FRESH_NEWS = "http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1&page=";
    public static final String URL_FRESH_NEWS_DETAIL = "http://i.jandan.net/?oxwlxojflwblxbsapi=get_post&include=content&id=";

    private String id;
    private String title;
    private String url;
    private String date;
    private String thumb_c;
    private String comment_count;

    private Author author;

    private CustomFields custom_fields;

    private Tags tags;

    public FreshNews(){
    }

    public static String getUrlFreshNews(int page){
        return URL_FRESH_NEWS + page;
    }

    public static String getUrlFreshNewsDetail(String id){
        return URL_FRESH_NEWS_DETAIL + id;
    }

    public static ArrayList<FreshNews> parse(JSONArray postsArray){
        ArrayList<FreshNews> freshNewses = new ArrayList<>();

        for(int i = 0; i < postsArray.length(); i++){
            FreshNews freshNews = new FreshNews();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            freshNews.setId(jsonObject.optString("id"));
            freshNews.setUrl(jsonObject.optString("url"));
            freshNews.setTitle(jsonObject.optString("title"));
            freshNews.setDate(jsonObject.optString("date"));
            freshNews.setComment_count(jsonObject.optString("comment_count"));
            freshNews.setAuthor(Author.parse(jsonObject.optJSONObject("author")));
            freshNews.setCustomFields(CustomFields.parse(jsonObject.optJSONObject("custom_fields")));
            freshNews.setTags(Tags.parse(jsonObject.optJSONArray("tags")));

            freshNewses.add(freshNews);
        }
        return freshNewses;
    }

    public static ArrayList<FreshNews> parseCache(JSONArray postsArray){
        ArrayList<FreshNews> freshNewses = new ArrayList<>();

        for(int i = 0; i < postsArray.length(); i++){
            FreshNews freshNews = new FreshNews();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            freshNews.setId(jsonObject.optString("id"));
            freshNews.setUrl(jsonObject.optString("url"));
            freshNews.setTitle(jsonObject.optString("title"));
            freshNews.setDate(jsonObject.optString("date"));
            freshNews.setComment_count(jsonObject.optString("comment_count"));
            freshNews.setAuthor(Author.parse(jsonObject.optJSONObject("author")));
            freshNews.setCustomFields(CustomFields.parseCache(jsonObject.optJSONObject("custom_fields")));
            freshNews.setTags(Tags.parseCache(jsonObject.optJSONObject("tags")));

            freshNewses.add(freshNews);
        }
        return freshNewses;
    }

    @Override
    public String toString() {
        return "FreshNews{" +
                "tags=" + tags +
                ", customFields=" + custom_fields +
                ", author=" + author +
                ", comment_count='" + comment_count + '\'' +
                ", thumb_c='" + thumb_c + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getThumb_c(){
        return thumb_c;
    }

    public void setThumb_c(String thumb_c){
        this.thumb_c = thumb_c;
    }

    public String getComment_count(){
        return comment_count;
    }

    public void setComment_count(String comment_count){
        this.comment_count = comment_count;
    }

    public Author getAuthor(){
        return author;
    }

    public void setAuthor(Author author){
        this.author = author;
    }

    public CustomFields getCustomFields(){
        return custom_fields;
    }

    public void setCustomFields(CustomFields custom_fields){
        this.custom_fields = custom_fields;
    }

    public Tags getTags(){
        return tags;
    }

    public void setTags(Tags tags){
        this.tags = tags;
    }

}
