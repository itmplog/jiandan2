package top.itmp.jiandan2.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.cache.FreshNewsCache;
import top.itmp.jiandan2.model.FreshNews;
import top.itmp.jiandan2.net.JSONParser;
import top.itmp.jiandan2.net.Request4FreshNews;
import top.itmp.jiandan2.net.RequestManager;
import top.itmp.jiandan2.ui.FreshNewsDetailActivity;
import top.itmp.jiandan2.utils.ImageLoadProxy;
import top.itmp.jiandan2.utils.NetworkUtils;
import top.itmp.jiandan2.utils.String2TimeUtils;
import top.itmp.jiandan2.utils.UI;

/**
 * Created by hz on 2016/5/29.
 */
public class FreshNewsAdapter extends RecyclerView.Adapter<FreshNewsAdapter.ViewHolder> {
    private int page;
    private int lastPosition;
    private boolean isLargeMode;
    private Activity mActivity;
    private DisplayImageOptions mOptions;
    private ArrayList<FreshNews> mFreshNews;

    public FreshNewsAdapter(Activity activity, boolean isLargeMode){
        this.mActivity = activity;
        this.isLargeMode = isLargeMode;

        mFreshNews = new ArrayList<>();

        int loadingResource = isLargeMode ? R.drawable.ic_loading_large : R.drawable.ic_loading_small;
        mOptions = ImageLoadProxy.getOptionsPictureList(loadingResource);
    }

    private void setAnimation(View view, int position){
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R
                .anim.item_bottom_in);
        view.startAnimation(animation);
    }

    @Override
    public void onViewDetachedFromWindow(FreshNewsAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (isLargeMode) {
            holder.card.clearAnimation();
        } else {
            holder.ll_content.clearAnimation();
        }
    }

    @Override
    public FreshNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = isLargeMode ? R.layout.item_fresh_news : R.layout.item_fresh_news_small;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FreshNewsAdapter.ViewHolder holder, final int position) {
        final FreshNews freshNews = mFreshNews.get(position);

        ImageLoadProxy.displayImage(freshNews.getCustomFields().getThumb_m(), holder.img, mOptions);
        holder.tv_title.setText(freshNews.getTitle());
        holder.tv_info.setText(freshNews.getAuthor().getName() + "@" + freshNews.getTags()
                .getTitle());
        //holder.tv_views.setText("浏览" + freshNews.getCustomFields().getViews() + "次");
        holder.tv_views.setText(freshNews.getComment_count() + "评论");
        holder.tv_date.setText(String2TimeUtils
                .dateString2GoodExperienceFormat(freshNews.getDate()));

        if (isLargeMode) {

            holder.tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ShareUtil.shareText(mActivity, freshNews.getTitle() + " " + freshNews.getUrl());
                }
            });

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(position);
                }
            });

            setAnimation(holder.card, position);
        } else {
            holder.ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(position);
                }
            });
            setAnimation(holder.ll_content, position);
        }

    }

    private void toDetailActivity(int position) {
        Intent intent = new Intent(mActivity, FreshNewsDetailActivity.class);
        intent.putExtra(FreshNewsDetailActivity.DATA_FRESH_NEWS, mFreshNews);
        intent.putExtra(FreshNewsDetailActivity.DATA_POSITION, position);
        mActivity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mFreshNews.size();
    }

    public void loadFirst() {
        page = 1;
        loadDataByNetworkType();
    }

    public void loadNextPage() {
        page++;
        loadDataByNetworkType();
    }

    private void loadDataByNetworkType() {

        if (NetworkUtils.isNetworkConnected()) {
            RequestManager.addRequest(new Request4FreshNews(FreshNews.getUrlFreshNews(page),
                    new Response.Listener<ArrayList<FreshNews>>() {
                        @Override
                        public void onResponse(ArrayList<FreshNews> response) {

                            //mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
                            //mLoadFinisCallBack.loadFinish(null);

                            if (page == 1) {
                                mFreshNews.clear();
                                FreshNewsCache.getInstance().clearAllCache();
                                lastPosition = -1;
                            }

                            mFreshNews.addAll(response);
                            notifyDataSetChanged();

                            FreshNewsCache.getInstance().addResultToCache(JSONParser.toString(response),
                                    page);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //mLoadResultCallBack.onError(LoadResultCallBack.ERROR_NET, error.getMessage());
                    //mLoadFinisCallBack.loadFinish(null);
                }
            }), mActivity);
        } else {
            //mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_OK, null);
            //mLoadFinisCallBack.loadFinish(null);

            if (page == 1) {
                mFreshNews.clear();
                UI.ShortToast("无网络，当前为缓存数据");
            }

            mFreshNews.addAll(FreshNewsCache.getInstance().getCacheByPage(page));
            notifyDataSetChanged();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_info)
        TextView tv_info;
        @BindView(R.id.tv_views)
        TextView tv_views;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @Nullable
        @BindView(R.id.tv_share)
        TextView tv_share;
        @BindView(R.id.img)
        ImageView img;
        @Nullable
        @BindView(R.id.card)
        CardView card;
        @Nullable
        @BindView(R.id.ll_content)
        LinearLayout ll_content;

        public ViewHolder(View contentView) {
            super(contentView);
            ButterKnife.bind(this, contentView);
        }
    }
}
