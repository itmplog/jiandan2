package top.itmp.jiandan2.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.base.BaseActivity;
import top.itmp.jiandan2.model.FreshNews;
import top.itmp.jiandan2.ui.fragment.FreshNewsDetailFragment;

/**
 * Created by hz on 2016/5/29.
 */
public class FreshNewsDetailActivity extends BaseActivity {

    public static final String DATA_FRESH_NEWS = "FreshNews";
    public static final String DATA_POSITION = "position";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fresh_news_detail);
        initView();
        initData();
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void initData() {
        ArrayList<FreshNews> freshNewses = (ArrayList<FreshNews>) getIntent().getSerializableExtra(DATA_FRESH_NEWS);
        int position = getIntent().getIntExtra(DATA_POSITION, 0);
        viewPager.setAdapter(new FreshNewsDetailAdapter(getSupportFragmentManager(), freshNewses));
        viewPager.setCurrentItem(position);
    }

    private class FreshNewsDetailAdapter extends FragmentPagerAdapter{
        private ArrayList<FreshNews> freshNewses;

        public FreshNewsDetailAdapter(FragmentManager fragmentManager, ArrayList<FreshNews> freshNewses){
            super(fragmentManager);
            this.freshNewses = freshNewses;
        }

        @Override
        public Fragment getItem(int position) {
            return FreshNewsDetailFragment.getInstance(freshNewses.get(position));
        }

        @Override
        public int getCount() {
            return freshNewses.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
