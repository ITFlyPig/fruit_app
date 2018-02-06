package com.wangyuelin.fruit.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangyuelin on 2018/2/1.
 *
 * 封装一些View常用的操作
 */

public class ViewUtil {

    /**
     * 隐藏所有的子View
     * @param viewGroup
     */
    public static void hiddenChildern(ViewGroup viewGroup){
        if (viewGroup == null){
            return;
        }
        int size = viewGroup.getChildCount();
        for (int i = 0; i < size; i++){
            viewGroup.getChildAt(i).setVisibility(View.INVISIBLE);
        }

    }
}
