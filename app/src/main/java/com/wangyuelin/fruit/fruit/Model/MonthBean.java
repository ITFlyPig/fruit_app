package com.wangyuelin.fruit.fruit.Model;

import java.util.List;

/**
 * Created by wangyuelin on 2018/2/1.
 */

public class MonthBean extends BaseBean {
    public List<String> fruits;
    public int monthNum;

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MonthBean[monthNum:+" + monthNum + "fruits:" + fruits + " ]");
        return stringBuilder.toString();
    }
}
