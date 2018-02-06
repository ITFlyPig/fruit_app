package com.wangyuelin.fruit.utils;

/**
 * Created by wangyuelin on 2018/2/1.
 */

public class Server {
    public interface Host{
        String host = "http://localhost:8080/fruit_crawer";
    }

    public interface Api{
        String GET_MONTH = "/fruit/getMonth";//获取月份对应的水果
    }
}
