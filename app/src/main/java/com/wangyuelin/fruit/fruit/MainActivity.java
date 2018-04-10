package com.wangyuelin.fruit.fruit;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;
import com.orhanobut.logger.Logger;
import com.wangyuelin.fruit.R;
import com.wangyuelin.fruit.common.net.okgo.BaseResponse;
import com.wangyuelin.fruit.common.net.okgo.JsonConvert;
import com.wangyuelin.fruit.common.net.okgo.LzyResponse;
import com.wangyuelin.fruit.fruit.Model.MonthBean;
import com.wangyuelin.fruit.fruit.Model.MonthResp;
import com.wangyuelin.fruit.fruit.Model.TestBean;
import com.wangyuelin.fruit.utils.Server;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseUIActivity {

    @BindView(R.id.ls_top)
    RecyclerView lsTop;
    @BindView(R.id.ls_left)
    RecyclerView lsLeft;
    @BindView(R.id.ls_right)
    RecyclerView lsRight;
    @BindView(R.id.ll_whole)
    LinearLayout llWhole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsShowTitleBar(false);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        llWhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrigin();
            }
        });



    }


    public void test() {
        //网络请求例子
//        OkGo.<BaseResponse<TestBean>>get("")//
//                .headers("aaa", "111")//
//                .params("bbb", "222")//
//                .converter(new JsonConvert<BaseResponse<TestBean>>() {
//                })//
//                .adapt(new ObservableBody<BaseResponse<TestBean>>())//
//                .subscribeOn(Schedulers.io())//
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) throws Exception {
////                        showLoading();
//                    }
//                })//
//                .map(new Function<BaseResponse<TestBean>, TestBean>() {
//                    @Override
//                    public TestBean apply(@NonNull BaseResponse<TestBean> response) throws Exception {
//                        return response.data;
//                    }
//                })//
//                .observeOn(AndroidSchedulers.mainThread())//
//                .subscribe(new Observer<TestBean>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        addDisposable(d);
//                    }
//
//                    @Override
//                    public void onNext(@NonNull TestBean List<MonthBean>) {
////                        handleResponse(List<MonthBean>);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        e.printStackTrace();            //请求失败
////                        showToast("请求失败");
////                        handleError(null);
//                    }
//
//                    @Override
//                    public void onComplete() {
////                        dismissLoading();
//                    }
//                });


    }


    private void getMonthData() {
        OkGo.<BaseResponse<List<MonthBean>>>get(Server.Host.host + Server.Api.GET_MONTH)
                .converter(new JsonConvert<BaseResponse<List<MonthBean>>>())
                .adapt(new ObservableBody<BaseResponse<List<MonthBean>>>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .map(new Function<BaseResponse<List<MonthBean>>, List<MonthBean>>() {
                    @Override
                    public List<MonthBean> apply(BaseResponse<List<MonthBean>> monthRespBaseResponse) throws Exception {
                        return monthRespBaseResponse.data;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MonthBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);

                    }

                    @Override
                    public void onNext(List<MonthBean> monthResp) {
                        Logger.d("wyl", monthResp.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getLocalizedMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    private void getMonthData2(){
        OkGo.<BaseResponse<List<MonthBean>>>get(Server.Host.host + Server.Api.GET_MONTH)
                .tag(this)
                .execute(new Callback<BaseResponse<List<MonthBean>>>() {
                    @Override
                    public void onStart(Request<BaseResponse<List<MonthBean>>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseResponse<List<MonthBean>>> response) {

                    }

                    @Override
                    public void onCacheSuccess(Response<BaseResponse<List<MonthBean>>> response) {

                    }

                    @Override
                    public void onError(Response<BaseResponse<List<MonthBean>>> response) {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public BaseResponse<List<MonthBean>> convertResponse(okhttp3.Response response) throws Throwable {
                        return null;
                    }
                });
    }

    
    private void  getOrigin(){
        OkGo.<LzyResponse<List<MonthBean>>>get(Server.Host.host + Server.Api.GET_MONTH)//
                .converter(new JsonConvert<LzyResponse<List<MonthBean>>>() {
                })//
                .adapt(new ObservableBody<LzyResponse<List<MonthBean>>>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })//
                .map(new Function<LzyResponse<List<MonthBean>>, List<MonthBean>>() {
                    @Override
                    public List<MonthBean> apply(@NonNull LzyResponse<List<MonthBean>> response) throws Exception {
                        return response.data;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<List<MonthBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull List<MonthBean> list) {
                        Logger.d("获取到的数据：" + list.toString());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();            //请求失败
                        Logger.d("onError");
                    }


                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");

                    }
                });
    }

}
