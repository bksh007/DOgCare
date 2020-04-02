package com.example.bksh1.dogcare.utils;


import io.reactivex.disposables.CompositeDisposable;

public class RxUtil {

    public static void disposeCompositeDisposable(CompositeDisposable compositeDisposable) {
        if (EmptyUtil.isNotNull(compositeDisposable) && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public static void clearCompositeDisposable(CompositeDisposable compositeDisposable) {
        if (EmptyUtil.isNotNull(compositeDisposable) && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
