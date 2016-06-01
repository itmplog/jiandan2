package top.itmp.jiandan2.base;

import android.support.v4.app.Fragment;

import com.android.volley.Request;

import top.itmp.jiandan2.net.RequestManager;

/**
 * Created by hz on 2016/4/5.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        TopApplication.getRefWatcher(getActivity()).watch(this);
    }

    public void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }
}
