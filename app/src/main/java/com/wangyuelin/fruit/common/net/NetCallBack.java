package com.wangyuelin.fruit.common.net;


/**
 * 最基本的网络请求回调接口
 * Created by wangyuelin on 2018/1/27.
 */

public interface NetCallBack<T> {

    void sucess(T data);
    void fail(T data);
    void start();
    void complete();
    void error(Throwable e);
}
