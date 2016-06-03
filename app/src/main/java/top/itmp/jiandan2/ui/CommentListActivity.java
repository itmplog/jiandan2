package top.itmp.jiandan2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.adapter.CommentAdapter;
import top.itmp.jiandan2.base.BaseActivity;
import top.itmp.jiandan2.callback.LoadResultCallBack;
import top.itmp.jiandan2.utils.UI;
import top.itmp.jiandan2.view.loading.LoadingView;

/**
 * Created by hz on 2016/6/2.
 */
public class CommentListActivity extends BaseActivity implements LoadResultCallBack {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading)
    LoadingView loadingView;

    private String thread_key;
    private String thread_id;
    private boolean isFromFreshNews;
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        initView();
        initData();
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("评论");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isFromFreshNews) {
                    mAdapter.loadData4FreshNews();
                } else {
                    mAdapter.loadData();
                }
            }
        });

    }


    @Override
    public void initData() {
        thread_key = getIntent().getStringExtra("thread_key");
        thread_id = getIntent().getStringExtra("thread_id");
        isFromFreshNews = getIntent().getBooleanExtra("is_from_fresh_news", false);

        if (isFromFreshNews) {
            mAdapter = new CommentAdapter(this, thread_id, isFromFreshNews, this);
            if (TextUtils.isEmpty(thread_id) || thread_id.equals("0")) {
                UI.ShortToast("禁止评论!");
                finish();
            }
        } else {
            mAdapter = new CommentAdapter(this, thread_key, isFromFreshNews, this);
            if (TextUtils.isEmpty(thread_key) || thread_key.equals("0")) {
                UI.ShortToast("禁止评论!");
                finish();
            }
        }
        recyclerView.setAdapter(mAdapter);
        if (isFromFreshNews) {
            mAdapter.loadData4FreshNews();
        } else {
            mAdapter.loadData();
        }
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (isFromFreshNews) {
                mAdapter.loadData4FreshNews();
            } else {
                mAdapter.loadData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, PushCommentActivity.class);
                intent.putExtra("thread_id", mAdapter.getThreadId());
                startActivityForResult(intent, 100);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(int result, Object object) {
        if (result == LoadResultCallBack.SUCCESS_NULL) {
            UI.ShortToast("无评论!");
        }
        loadingView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(int code, String msg) {
        swipeRefreshLayout.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
        UI.ShortToast("加载失败!");
    }
}
