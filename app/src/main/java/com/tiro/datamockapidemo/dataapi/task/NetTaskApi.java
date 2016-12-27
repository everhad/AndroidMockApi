package com.tiro.datamockapidemo.dataapi.task;

import com.tiro.datamockapidemo.data.Task;
import com.tiro.datamockapidemo.dataapi.DataApiCallback;

import java.util.List;

/**
 * Real implement of ITaskApi.
 */
public class NetTaskApi implements ITaskApi {
    @Override
    public void getTasks(DataApiCallback<List<Task>> callback) {
        // for now, there is no actual server api, notify the error.
        callback.onError(new Exception("No Data Api."));
    }
}
