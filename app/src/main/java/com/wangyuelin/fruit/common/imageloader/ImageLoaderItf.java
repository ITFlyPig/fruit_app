package com.wangyuelin.fruit.common.imageloader;

import android.widget.ImageView;

/**
 * Created by wangyuelin on 2018/1/27.
 */

public interface ImageLoaderItf {
    void setImageUri(ImageView imageView,  String url, int placeholderResId);
    void setImageUri(ImageView imageView, String url, int placeholderResId, int errorResId);
}
