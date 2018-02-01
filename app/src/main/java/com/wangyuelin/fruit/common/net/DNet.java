package com.wangyuelin.fruit.common.net;

import com.wangyuelin.fruit.common.net.okgo.OkgoDNetItfImpl;

import java.util.Map;

/**
 * Created by wangyuelin on 2018/1/29.
 * 提供一个统一的接口，屏蔽是哪个具体的库实现网络请求，以后更换网络请求库的时候方便
 */

public class DNet {
    private static DNetItf dNetItf;

    static {
        dNetItf = new OkgoDNetItfImpl();
    }

    public static <T> void  get(String url, Map<String, String> params, NetCallBack netCallBack){
        dNetItf.<T>get(url, params, netCallBack);

    }
}
