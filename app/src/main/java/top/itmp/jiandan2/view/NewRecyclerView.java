package top.itmp.jiandan2.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nostra13.universalimageloader.core.ImageLoader;

import top.itmp.jiandan2.callback.LoadCompleteCallBack;
import top.itmp.jiandan2.callback.LoadMoreCallBack;
import top.itmp.jiandan2.utils.ImageLoadProxy;

/**
 * Created by hz on 2016/6/1.
 */
public class NewRecyclerView extends RecyclerView implements LoadCompleteCallBack {

    private LoadMoreCallBack loadMoreCallBack;
    private boolean isLoadingMore;

    public NewRecyclerView(Context context) {
        this(context, null);
    }

    public NewRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NewRecyclerView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        isLoadingMore = false;
        addOnScrollListener(new NewRecyclerView.LoadScrollListener(ImageLoadProxy.getImageLoader(), true, true));
    }

    public void setOnPauseParams(boolean pauseOnScroll, boolean pauseOnFling) {
        addOnScrollListener(new NewRecyclerView.LoadScrollListener(ImageLoadProxy.getImageLoader(), pauseOnScroll, pauseOnFling));
    }

    public void setLoadMoreCallBack(LoadMoreCallBack loadMoreCallBack) {
        this.loadMoreCallBack = loadMoreCallBack;
    }

    @Override
    public void loadComplete(Object object) {
        isLoadingMore = false;
    }

    private class LoadScrollListener extends OnScrollListener {
        private ImageLoader imageLoader;
        private final boolean pauseOnScroll;
        private final boolean pauseOnFling;

        public LoadScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
            super();
            this.pauseOnScroll = pauseOnScroll;
            this.pauseOnFling = pauseOnFling;
            this.imageLoader = imageLoader;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = NewRecyclerView.this.getAdapter().getItemCount();

                if (loadMoreCallBack != null && !isLoadingMore && lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    loadMoreCallBack.loadMore();
                    isLoadingMore = true;
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //super.onScrollStateChanged(recyclerView, newState);

            if (imageLoader != null) {
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        imageLoader.resume();
                        break;
                    case SCROLL_STATE_DRAGGING:
                        if (pauseOnScroll) {
                            imageLoader.pause();
                        } else {
                            imageLoader.resume();
                        }
                        break;
                    case SCROLL_STATE_SETTLING:
                        if (pauseOnFling) {
                            imageLoader.pause();
                        } else {
                            imageLoader.resume();
                        }
                        break;
                }
            }
        }
    }

}
