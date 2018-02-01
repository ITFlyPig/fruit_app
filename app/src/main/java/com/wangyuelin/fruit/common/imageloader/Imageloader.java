package com.wangyuelin.fruit.common.imageloader;

import android.widget.ImageView;

/**
 *
 * 规定统一的图片下载的接口
 * Created by wangyuelin on 2018/1/27.
 */

public class Imageloader {
    private static ImageLoaderItf mLoader;

    static {
        mLoader = new PicassoImpl();
    }


    /**
     * 加载图片
     * @param imageView
     * @param url
     * @param placeholderResId
     */
    public static void setImageUri(ImageView imageView, String url, int placeholderResId) {
        mLoader.setImageUri(imageView, url, placeholderResId);

    }

    public static void setImageUri(ImageView imageView, String url, int placeholderResId, int errorResId){
        mLoader.setImageUri(imageView, url, placeholderResId, errorResId);

    }


}
