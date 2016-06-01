package top.itmp.jiandan2.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import top.itmp.jiandan2.model.FreshNews;

/**
 * Created by hz on 2016/5/29.
 */
public class Request4FreshNews extends Request<ArrayList<FreshNews>> {
    private Response.Listener<ArrayList<FreshNews>> listener;

    public Request4FreshNews(String url, Response.Listener<ArrayList<FreshNews>> listener,
                             Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<ArrayList<FreshNews>> parseNetworkResponse(NetworkResponse response) {
        try {
            String result = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject resultObject = new JSONObject(result);
            JSONArray resultArray = resultObject.optJSONArray("posts");
            return Response.success(FreshNews.parse(resultArray), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<FreshNews> response) {
        listener.onResponse(response);
    }
}
