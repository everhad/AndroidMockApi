package com.tiro.datamockapidemo.mockapi;

import com.tiro.datamockapidemo.dataapi.task.ITaskApi;
import com.tiro.datamockapidemo.mockapi.task.MockTaskApi;

import java.util.HashMap;

/**
 * All mock DataApi instance will be fetched through here.
 */
public class MockApiManager {
    private static final MockApiManager INSTANCE = new MockApiManager();
    private HashMap<String, BaseMockApi> mockApis;

    private MockApiManager() {}

    /**
     * Get a mock instance for the interface that dataApiClass specified.
     * @param dataApiClass DataApi interface
     */
    public static <T> T getMockApi(Class<T> dataApiClass) {
        if (dataApiClass == null) return null;

        String key = dataApiClass.getName();

        try {
            T mock = (T) getInstance().mockApis.get(key);
            return mock;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * setup all “mock apis”.
     */
    private void initApiTable() {
        mockApis = new HashMap<>();

        mockApis.put(ITaskApi.class.getName(), new MockTaskApi());
    }

    private static MockApiManager getInstance() {
        if (INSTANCE.mockApis == null) {
            synchronized (MockApiManager.class) {
                if (INSTANCE.mockApis == null) {
                    INSTANCE.initApiTable();
                }
            }
        }

        return INSTANCE;
    }
}
