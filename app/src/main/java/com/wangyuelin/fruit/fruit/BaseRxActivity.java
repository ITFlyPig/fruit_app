package com.wangyuelin.fruit.fruit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangyuelin on 2018/2/1.
 *
 * 管理rx的注册和解绑的，避免内存泄露
 */

public class BaseRxActivity extends BaseActivtiy {
    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }
}
