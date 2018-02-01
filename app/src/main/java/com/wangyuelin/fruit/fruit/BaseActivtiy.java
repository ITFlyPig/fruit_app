package com.wangyuelin.fruit.fruit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wangyuelin.fruit.utils.systembar.SystemBarHelper;

/**
 * Created by wangyuelin on 2018/1/29.
 * 数据请求失败和成功的提示
 *
 */

public class BaseActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 设置沉浸模式
         */
        SystemBarHelper.setAdaptStatusBar(getWindow());
    }


}
