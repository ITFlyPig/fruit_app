package com.wangyuelin.fruit.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.wangyuelin.fruit.common.imageloader.Imageloader;

/**
 * Imageview集成了下载和形状的功能
 * 集成圆形，方形，超椭圆等样式
 */
public class DImageView extends ShapeImageView {

    public DImageView(Context context) {
        this(context, null);
    }

    public DImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageUri(String url, int placeholderResId) {
        Imageloader.setImageUri(this, url, placeholderResId);
    }

    public void setImageUri(String url, int placeholderResId, int errorResId) {
        Imageloader.setImageUri(this, url, placeholderResId, errorResId);
    }

    /**
     * 设置圆形uri
     *
     * @param url
     * @param placeholderResId
     */
    public void setCircleImageUri(String url, int placeholderResId) {
        setShapeType(ShapeType.CIRCLE);
        setImageUri(url, placeholderResId);
    }

    /**
     * 设置超椭圆uri
     *
     * @param url
     * @param placeholderResId
     */
    public void setOvalImageUri(String url, int placeholderResId) {
        setShapeType(ShapeType.SUPEROVAL);
        setImageUri(url, placeholderResId);
    }
}
