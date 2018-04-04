package com.chhd.android.common.util.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/18
 * desc   :
 */

public class ImageLoader {

    private static ImageLoaderConfig config = new ImageLoaderConfig();

    public static void init(ImageLoaderConfig config) {
        ImageLoader.config = config;
    }

    @SuppressLint("StaticFieldLeak")
    private static ImageLoader imageLoader = new ImageLoader();

    public static ImageLoader getInstance() {
        return new ImageLoader();
    }

    private Activity activity;
    private Fragment fragment;
    private Object model;
    private boolean isAnimation = true; // 加载动画
    private int placeholderId; // 加载占位图
    private int errorId; // 错误占位图

    private ImageLoader() {
    }


    public ImageLoader with(Activity activity) {
        this.activity = activity;
        return this;
    }

    public ImageLoader with(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public ImageLoader load(Object model) {
        this.model = model;
        return this;
    }

    private ImageLoader dontAnimate() {
        this.isAnimation = false;
        return this;
    }

    public ImageLoader placeholderId(int placeholderId) {
        this.placeholderId = placeholderId;
        return this;
    }

    public ImageLoader errorId(int errorId) {
        this.errorId = errorId;
        return this;
    }

    public void into(ImageView imageView) {

        RequestManager requestManager;
        if (activity != null) {
            requestManager = Glide.with(activity);
        } else if (fragment != null) {
            requestManager = Glide.with(fragment);
        } else {
            requestManager = Glide.with(imageView);
        }

        RequestBuilder requestBuilder = requestManager.load(model);

        if (placeholderId != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(placeholderId));
        } else if (config.getPlaceholderId() != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(config.getPlaceholderId()));
        }
        if (errorId != 0) {
            requestBuilder.apply(RequestOptions.errorOf(errorId));
        } else if (config.getErrorId() != 0) {
            requestBuilder.apply(RequestOptions.errorOf(config.getErrorId()));
        }
        String appCompatImageViewClazzName = "android.support.v7.widget.AppCompatImageView";
        String imageViewClazzName = "android.widget.ImageView";
        String clazzName = imageView.getClass().getName();
        if (config.isAnimation() && isAnimation
                && (appCompatImageViewClazzName.equals(clazzName) || imageViewClazzName.equals(clazzName))) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        }
        if (config.isNoPhoto() && isMobileConnected(imageView.getContext()) && isUrl(model)) {
            requestBuilder.apply(new RequestOptions().onlyRetrieveFromCache(true));
        }

        requestBuilder.into(imageView);

        reset();
    }

    private void reset() {
        activity = null;
        fragment = null;
        model = null;
        isAnimation = true; // 加载动画
        placeholderId = 0; // 加载占位图
        errorId = 0; // 错误占位图
    }

    /**
     * 是否移动数据连接
     */
    private boolean isMobileConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }

    private boolean isUrl(Object model) {
        if (model instanceof String) {
            String str = (String) model;
            if (str.startsWith("http://") || str.startsWith("https://")) return true;
        }
        return false;
    }

}
