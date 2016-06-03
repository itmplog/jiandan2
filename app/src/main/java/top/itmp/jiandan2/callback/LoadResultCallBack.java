package top.itmp.jiandan2.callback;

/**
 * Created by hz on 2016/6/1.
 */
public interface LoadResultCallBack {
    int SUCCESS = 0;
    int SUCCESS_NULL = 1;
    int ERROR = 2;

    void onSuccess(int result, Object object);

    void onError(int code, String message);
}
