package com.wangyuelin.fruit.common.net.okgo;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okrx2.adapter.ObservableBody;
import com.wangyuelin.fruit.common.net.DNetItf;
import com.wangyuelin.fruit.common.net.NetCallBack;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangyuelin on 2018/1/27.
 */

public class OkgoDNetItfImpl<T> implements DNetItf<T> {


    /**
     * 一般的get请求使用这个已经满足了
     * @param url
     * @param params
     * @param headers
     * @param netCallBeack
     * @param <T>
     */
    @Override
    public <T> void get(String url, Map<String, String> params, Map<String, String> headers, final NetCallBack netCallBeack) {
        HttpHeaders httpHeaders = getHeaders(headers);
        OkGo.<BaseResponse<T>>get(url)//
                .headers(httpHeaders)//
                .params(params)//
                .converter(new JsonConvert<BaseResponse<T>>() {
                })//
                .adapt(new ObservableBody<BaseResponse<T>>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })//
                .map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull BaseResponse<T> response) throws Exception {
                        return response.data;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull T serverModel) {
//                        handleResponse(serverModel);
                        if (netCallBeack != null){
                            netCallBeack.sucess(serverModel);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();            //请求失败
//                        showToast("请求失败");
//                        handleError(null);
                        if (netCallBeack != null){
                            netCallBeack.error(e);
                        }
                    }

                    @Override
                    public void onComplete() {
//                        dismissLoading();
                        if (netCallBeack != null){
                            netCallBeack.complete();
                        }
                    }
                });



    }

    @Override
    public <T> void get(String url, Map<String, String> params, NetCallBack netCallBack) {
        get(url, params, null, netCallBack);

    }


    /**
     * 据map获取具体的网络请求头
     * @param headerMap
     * @return
     */
    private HttpHeaders getHeaders(Map<String, String> headerMap){
        if (headerMap == null || headerMap.size() == 0){
            return null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> item:headerMap.entrySet()){
            String key = item.getKey();
            String value =item.getValue();
            httpHeaders.put(key, value);
        }
        return httpHeaders;

    }
}
