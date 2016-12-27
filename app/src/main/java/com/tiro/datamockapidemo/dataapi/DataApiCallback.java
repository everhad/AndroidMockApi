package com.tiro.datamockapidemo.dataapi;

import rx.Subscriber;

/**
 * Callback of data access (network) operation，it is async.
 */
public interface DataApiCallback<T>  {

    void onSuccess(T data);

    void onError(Throwable e);

    void onStart();
}
