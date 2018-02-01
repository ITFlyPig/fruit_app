package com.wangyuelin.fruit.common.imageloader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Picasso实现图片下载的接口
 * Created by wangyuelin on 2018/1/27.
 */

public class PicassoImpl implements ImageLoaderItf {

    @Override
    public void setImageUri(ImageView imageView, String url, int placeholderResId) {
        Picasso.with(imageView.getContext()).load(url).placeholder(placeholderResId).into(imageView);

    }

    @Override
    public void setImageUri(ImageView imageView, String url, int placeholderResId, int errorResId) {
        Picasso.with(imageView.getContext()).load(url).placeholder(placeholderResId).error(errorResId).into(imageView);
    }
}
