package top.itmp.jiandan2.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import top.itmp.jiandan2.base.TopApplication;

/**
 * Created by hz on 2016/5/28.
 */
public class RequestManager {

    public static final int OUT_TIME = 10000;
    public static final int TIMES_OF_RETRY = 1;

    public static RequestQueue mRequestQueue = TopApplication.getRequestQueue();

    private RequestManager() {
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }

        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME,
                TIMES_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
