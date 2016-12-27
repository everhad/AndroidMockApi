package com.tiro.datamockapidemo.mockapi;

import com.tiro.datamockapidemo.dataapi.task.ITaskApi;
import com.tiro.datamockapidemo.mockapi.task.MockTaskApi;

import java.util.HashMap;

/**
 * All mock DataApi instance will be fetched through here.
 */
public class MockApiManager {

    /**
     * Always return null.  
     */
    public static <T> T getMockApi(Class<T> dataApiClass) {
        return null;
    }   
}
