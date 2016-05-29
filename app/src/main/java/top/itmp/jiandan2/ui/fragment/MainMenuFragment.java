package top.itmp.jiandan2.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.base.BaseFragment;
import top.itmp.jiandan2.model.MenuItem;
import top.itmp.jiandan2.ui.MainActivity;
import top.itmp.jiandan2.ui.SettingsActivity;

/**
 * Created by hz on 2016/5/29.
 */
public class MainMenuFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_container)
    RelativeLayout mRelativeLayout;

    private LinearLayoutManager mLinearLayoutManager;
    private MainActivity mMainActivity;
    private MenuAdapter mMenuAdapter;
    private MenuItem.FragmentType currentFragment = MenuItem.FragmentType.FreshNews;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            mMainActivity = (MainActivity) context;
        } else {
            throw new IllegalArgumentException("The Activity must be a MainActivity!!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menus, container, false);
        ButterKnife.bind(this, rootView);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                mMainActivity.closeSlidingPaneLayout();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMenuAdapter = new MenuAdapter();
        addAllMenuItems(mMenuAdapter);
        mRecyclerView.setAdapter(mMenuAdapter);
    }

    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<MenuItem> mMenuItems;

        public MenuAdapter() {
            mMenuItems = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final MenuItem menuItem = mMenuItems.get(position);

            holder.mTextView.setText(menuItem.getTitle());
            holder.mImageView.setImageResource(menuItem.getResourceId());
            holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (currentFragment != menuItem.getType()) {
                            Fragment fragment = (Fragment) Class.forName(menuItem.getFragment()
                                    .getName()).newInstance();
                            mMainActivity.setCurrentFragment(menuItem.getType());
                            currentFragment = menuItem.getType();
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    mMainActivity.closeSlidingPaneLayout();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mMenuItems.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;
        RelativeLayout mRelativeLayout;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.img_menu);
            mTextView = (TextView)view.findViewById(R.id.tv_title);
            mRelativeLayout = (RelativeLayout)view.findViewById(R.id.rl_container);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (sp.getBoolean("enable_sister", false) && mMenuAdapter.mMenuItems.size() == 4) {
            addAllMenuItems(mMenuAdapter);
            mMenuAdapter.notifyDataSetChanged();
        }else if(!sp.getBoolean("enable_sister", false) && mMenuAdapter.mMenuItems.size() == 5){
            addMenuItemsNoSister(mMenuAdapter);
            mMenuAdapter.notifyDataSetChanged();
        }
    }

    private void addAllMenuItems(MenuAdapter mAdapter) {
        mAdapter.mMenuItems.clear();
        mAdapter.mMenuItems.add(new MenuItem("新鲜事", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.FreshNews,
                FreshNewsFragment.class));
        mAdapter.mMenuItems.add(new MenuItem("无聊图", R.drawable.ic_mood_white_24dp, MenuItem.FragmentType.BoringPicture,
                PictureFragment.class));
        mAdapter.mMenuItems.add(new MenuItem("妹子图", R.drawable.ic_local_florist_white_24dp, MenuItem.FragmentType.Sister,
                SisterFragment.class));
        mAdapter.mMenuItems.add(new MenuItem("段子", R.drawable.ic_chat_white_24dp, MenuItem.FragmentType.Joke, JokeFragment
                .class));
        mAdapter.mMenuItems.add(new MenuItem("小电影", R.drawable.ic_movie_white_24dp, MenuItem.FragmentType.Video,
                VideoFragment.class));
    }

    private void addMenuItemsNoSister(MenuAdapter mAdapter) {
        mAdapter.mMenuItems.clear();
        mAdapter.mMenuItems.add(new MenuItem("新鲜事", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.FreshNews,
                FreshNewsFragment.class));
        mAdapter.mMenuItems.add(new MenuItem("无聊图", R.drawable.ic_mood_white_24dp, MenuItem.FragmentType.BoringPicture,
                PictureFragment.class));
        mAdapter.mMenuItems.add(new MenuItem("段子", R.drawable.ic_chat_white_24dp, MenuItem.FragmentType.Joke, JokeFragment
                .class));
        mAdapter.mMenuItems.add(new MenuItem("小电影", R.drawable.ic_movie_white_24dp, MenuItem.FragmentType.Video,
                VideoFragment.class));
    }
}
