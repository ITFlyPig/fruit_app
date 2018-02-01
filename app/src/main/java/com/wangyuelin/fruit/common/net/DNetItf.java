package com.wangyuelin.fruit.common.net;

import java.util.Map;

/**
 * Created by wangyuelin on 2018/1/27.
 */

public interface DNetItf<T> {
    <T> void get(String url, Map<String, String> params, Map<String, String> headers, NetCallBack netCallBack);

    <T> void get(String url, Map<String, String> params, NetCallBack netCallBack);

}
