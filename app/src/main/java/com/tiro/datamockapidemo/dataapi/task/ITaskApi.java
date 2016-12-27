package com.tiro.datamockapidemo.dataapi.task;

import com.tiro.datamockapidemo.data.Task;
import com.tiro.datamockapidemo.dataapi.DataApiCallback;

import java.util.List;

/**
 * interface for DataApi about a Task, include methods of accessing Task.
 */
public interface ITaskApi {

    /**
     * Get all Tasks (from server).
     * @param callback callback of data access (network) operation.
     */
    void getTasks(DataApiCallback<List<Task>> callback);
}
