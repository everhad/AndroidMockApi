package com.tiro.datamockapidemo.mockapi;

import android.util.Log;

import com.tiro.datamockapidemo.dataapi.DataApiCallback;
import com.tiro.datamockapidemo.mockapi.IMockApiStrategy.Response;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Base class for kinds of MockDataApi. Implements some common logic.
 */
public abstract class BaseMockApi {
    protected int mCallCount;
    private IMockApiStrategy mStrategy;
    private Response mResponse = new Response();

    /**
     * Call this to get a mock response.
     */
    public Response onResponse() {
        if (mStrategy == null) {
            mStrategy = getMockApiStrategy();
        }

        if (mStrategy != null) {
            mStrategy.onResponse(mCallCount, mResponse);
            mCallCount++;
        }

        return mResponse;
    }

    /**
     * Get the response Strategy. SubClass can override.
     */
    protected IMockApiStrategy getMockApiStrategy() {
        return new WheelApiStrategy();
    }

    /**
     * Give error response. No data.
     * @param callback callback from UI layer or ..
     * @param response mock response
     */
    protected void giveErrorResult(final DataApiCallback<?> callback, Response response) {
        Action1<Object> onNext = null;

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                callback.onStart();
            }
        });

        switch (response.state) {
            case Response.STATE_NETWORK_ERROR:
                onNext = new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        callback.onError(new IOException("mock network error."));
                    }
                };

                break;
            case Response.STATE_SERVER_ERROR:
                onNext = new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        callback.onError(new IOException("mock server error."));
                    }
                };
                break;
        }

        if (onNext != null) {
            Observable.just(10086)
                    .delay(response.delayMillis, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext);
        }
    }

    /**
     * Give success response, Have data.
     * @param dataMethod
     * @param callback
     * @param response
     * @param <T>
     */
    public <T> void giveSuccessResult(final Func0<T> dataMethod, final DataApiCallback<T> callback, final Response response) {
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        Log.d("MOCK", "onNext Thread = " + Thread.currentThread().getName());
                        subscriber.onNext(dataMethod.call());
                        subscriber.onCompleted();
                    }
                }).
                delay(response.delayMillis, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubcriber(callback));
            }
        });
    }

    /**
     * Helper class to wrap the DataApiCallback.
     * @param <T>
     */
    private static class ApiSubcriber<T> extends Subscriber<T> {
        private DataApiCallback<T> callback;

        public ApiSubcriber(DataApiCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onStart() {
            callback.onStart();
        }

        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {
            callback.onError(e);
        }

        @Override
        public void onNext(T data) {
            callback.onSuccess(data);
        }
    }
}
