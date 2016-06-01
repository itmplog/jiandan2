package top.itmp.jiandan2.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.adapter.FreshNewsAdapter;
import top.itmp.jiandan2.base.BaseFragment;
import top.itmp.jiandan2.utils.UI;

/**
 * Created by hz on 2016/5/29.
 */
public class FreshNewsFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private FreshNewsAdapter mFreshNewsAdapter;
    private boolean isLargeMode = true;

    public FreshNewsFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fresh_news, container, false);
        ButterKnife.bind(this, rootView);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isLargeMode = sp.getBoolean("enable_big", true);

        mSwipeRefreshLayout.setPadding(0, UI.getStatusBarHeight(getContext()), 0, UI.getNavigationHeight(getContext()));
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(isLargeMode != sp.getBoolean("enable_big", true)){
            isLargeMode = !isLargeMode;
            initRecycleView(isLargeMode);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setHasFixedSize(false);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFreshNewsAdapter.loadFirst();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.setOnPauseListenerParams(false, true);

        initRecycleView(isLargeMode);
    }

    private void initRecycleView(boolean isLargeMode){
        mFreshNewsAdapter = new FreshNewsAdapter(getActivity(), isLargeMode);
        mRecyclerView.setAdapter(mFreshNewsAdapter);
        mFreshNewsAdapter.loadFirst();
        //loading.start();
    }
}
