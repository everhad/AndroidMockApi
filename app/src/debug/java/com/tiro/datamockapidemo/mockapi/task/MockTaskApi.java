package com.tiro.datamockapidemo.mockapi.task;

import com.tiro.datamockapidemo.data.Task;
import com.tiro.datamockapidemo.dataapi.DataApiCallback;
import com.tiro.datamockapidemo.dataapi.task.ITaskApi;
import com.tiro.datamockapidemo.mockapi.BaseMockApi;
import com.tiro.datamockapidemo.mockapi.IMockApiStrategy.Response;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func0;

/**
 * Mock implement for ITaskApi.
 */
public class MockTaskApi extends BaseMockApi implements ITaskApi {

    @Override
    public void getTasks(DataApiCallback<List<Task>> callback) {
        Response response = onResponse();

        if (response.state == Response.STATE_SUCCESS) {
            Func0<List<Task>> mockTasks = new Func0<List<Task>>() {
                @Override
                public List<Task> call() {
                    // here to give some mock data, you can get it from a json file —— if there is.
                    ArrayList<Task> tasks = new ArrayList<>();
                    int start = (mCallCount - 1) * 6;
                    for (int i = start; i < start + 6; i++) {
                        Task task = new Task();
                        task.name = "Task - " + i;

                        tasks.add(task);
                    }

                    return tasks;
                }
            };

            giveSuccessResult(mockTasks, callback, response);
        } else {

            giveErrorResult(callback, response);
        }
    }
}
