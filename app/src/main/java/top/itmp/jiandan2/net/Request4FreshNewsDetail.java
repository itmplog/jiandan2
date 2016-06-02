package top.itmp.jiandan2.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by hz on 2016/6/2.
 */
public class Request4FreshNewsDetail extends Request<String> {
    private Response.Listener<String> listener;

    public Request4FreshNewsDetail(String url,
                                   Response.Listener<String> listener,
                                   Response.ErrorListener errorListener){
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try{
            String result = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(result);

            if(jsonObject.optString("status").equals("ok")){
                JSONObject content = jsonObject.optJSONObject("post");
                return Response.success(content.optString("content"), HttpHeaderParser.parseCacheHeaders(response));
            }else{
                return Response.success("error", HttpHeaderParser.parseCacheHeaders(response));
            }
        }catch (Exception e){
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
