package top.itmp.jiandan2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import top.itmp.jiandan2.R;

/**
 * Created by hz on 2016/5/28.
 */
public class ImageLoadProxy {
    private static final int MAX_DISK_CACHE = 1024 * 1024 * 50;
    private static final int MAX_MEMORY_CACHE = 1024 * 1024 * 10;

    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            synchronized (ImageLoadProxy.class) {
                if (imageLoader == null)
                    imageLoader = ImageLoader.getInstance();
            }
        }
        return imageLoader;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.diskCacheSize(MAX_DISK_CACHE);
        builder.memoryCacheSize(MAX_MEMORY_CACHE);
        builder.memoryCache(new LruMemoryCache(MAX_DISK_CACHE));

        getImageLoader().init(builder.build());
    }

    public static void displayImage(String url, ImageView imageView, DisplayImageOptions options) {
        imageLoader.displayImage(url, imageView, options);
    }

    public static void displayImage(String url, ImageView imageView, DisplayImageOptions options, SimpleImageLoadingListener listener) {
        imageLoader.displayImage(url, imageView, options, listener);
    }

    public static void displayHeaderIcon(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, getOptionsHeader());
    }

    public static void displayImageDetail(String url, ImageView imageView, SimpleImageLoadingListener loadingListener) {
        imageLoader.displayImage(url, imageView, getOptionsExactlyType(), loadingListener);
    }

    public static void displayImageList(String url, ImageView imageView, int loadingResource, SimpleImageLoadingListener loadingListener, ImageLoadingProgressListener progressListener) {
        imageLoader.displayImage(url, imageView, getOptionsPictureList(loadingResource), loadingListener, progressListener);
    }

    public static void displayImageWithLoadingPicture(String url, ImageView imageView, int loadingResource) {
        imageLoader.displayImage(url, imageView, getOptionsPictureList(loadingResource));
    }

    public static void loadImageFromLocalCache(String url, SimpleImageLoadingListener loadingListener) {
        imageLoader.loadImage(url, getOptionsExactlyType(), loadingListener);
    }

    public static DisplayImageOptions getOptionsExactlyType() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    public static DisplayImageOptions getOptionsHeader() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();
    }

    public static DisplayImageOptions getOptionsPictureList(int loadingResource) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                //.showImageOnLoading(loadingResource)
                .showImageForEmptyUri(loadingResource)
                .showImageOnFail(loadingResource)
                .build();
    }
}
