package top.itmp.jiandan2.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;

import top.itmp.jiandan2.R;
import top.itmp.jiandan2.net.RequestManager;

/**
 * Created by hz on 2016/4/5.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public abstract void initView();

    public abstract void initData();

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    public void transFragment(int view_id, Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(view_id, fragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TopApplication.getRefWatcher(this).watch(this);
        RequestManager.cancelAll(this);
    }

    public void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }
}
