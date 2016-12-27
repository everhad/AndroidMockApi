package com.tiro.datamockapidemo.dataapi;

import com.tiro.datamockapidemo.BuildConfig;
import com.tiro.datamockapidemo.dataapi.task.ITaskApi;
import com.tiro.datamockapidemo.dataapi.task.NetTaskApi;
import com.tiro.datamockapidemo.mockapi.MockApiManager;

/**
 * All DataApi instance will be fetched through here.
 */
public class DataApiManager {
    /**
     * As simple example, when in debug build, we can use mock.
     */
    private static final boolean MOCK_ENABLE = BuildConfig.DEBUG;

    /**
     * Get a DataApi instance of Task.
     * @return ITaskApi instance, could be a mock instance.
     */
    public static ITaskApi ofTask() {
        if (MOCK_ENABLE) {
            ITaskApi api = MockApiManager.getMockApi(ITaskApi.class);
            if (api != null) return api;
        }

        return new NetTaskApi();
    }
}
