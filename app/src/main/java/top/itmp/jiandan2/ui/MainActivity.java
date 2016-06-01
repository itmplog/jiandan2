package top.itmp.jiandan2.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.base.BaseActivity;
import top.itmp.jiandan2.model.MenuItem;
import top.itmp.jiandan2.ui.fragment.FreshNewsFragment;
import top.itmp.jiandan2.ui.fragment.MainMenuFragment;
import top.itmp.jiandan2.utils.UI;
import top.itmp.jiandan2.views.PagerEnabledSlidingPaneLayout;

public class MainActivity extends BaseActivity {

    @BindView(R.id.sliding_pane_layout)
    PagerEnabledSlidingPaneLayout mPagerEnabledSlidingPaneLayout;
    @BindView(R.id.left)
    LinearLayout left;

    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        //mPagerEnabledSlidingPaneLayout = (PagerEnabledSlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        //mListView = (ListView) findViewById(R.id.left_pane);
        //mFrameLayout = (FrameLayout)findViewById(R.id.context);
        //mViewPager = (ViewPager) findViewById(R.id.pager);
        //mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.titles);

        left.setPadding(8, UI.getStatusBarHeight(this.getResources()), 0, UI.getNavigationHeight(this));

        mPagerEnabledSlidingPaneLayout.setSliderFadeColor(ContextCompat.getColor(this, R.color.translucent));

        transFragment(R.id.menus, new MainMenuFragment());
        transFragment(R.id.content, new FreshNewsFragment());
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出、", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void closeSlidingPaneLayout() {
        mPagerEnabledSlidingPaneLayout.closePane();
    }

    public void setCurrentFragment(MenuItem.FragmentType type) {
        //mViewPager.setCurrentItem(type.ordinal());
    }

    private static class ColorPagerAdapter extends PagerAdapter {
        private ArrayList<Pair<String, Integer>> mEntries = new ArrayList<>();

        public void add(String title, int color) {
            mEntries.add(new Pair(title, color));
        }

        @Override
        public int getCount() {
            return mEntries.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final View view = new View(container.getContext());
            view.setBackgroundColor(mEntries.get(position).second);

            // Unlike ListView adapters, the ViewPager adapter is responsible
            // for adding the view to the container.
            container.addView(view);

            return new ViewHolder(view, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // The adapter is also responsible for removing the view.
            container.removeView(((ViewHolder) object).view);
        }

        @Override
        public int getItemPosition(Object object) {
            return ((ViewHolder) object).position;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return ((ViewHolder) object).view == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mEntries.get(position).first;
        }

        private static class ViewHolder {
            final View view;
            final int position;

            public ViewHolder(View view, int position) {
                this.view = view;
                this.position = position;
            }
        }
    }


}
