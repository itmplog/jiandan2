package top.itmp.jiandan2.base;

import android.support.v4.app.Fragment;

/**
 * Created by hz on 2016/4/5.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        TopApplication.getRefWatcher(getActivity()).watch(this);
    }
}
