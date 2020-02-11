package com.example.ali_pay;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    private int poolSize = 2;

    private int maxPoolSize = poolSize;

    private ExecutorService executorService;

    private void initThreadUtil() {
        executorService = new ThreadPoolExecutor(
                poolSize,
                maxPoolSize,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(512),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    private ThreadUtil() {
    }

    private static ThreadUtil instance;

    public static ThreadUtil getInstance() {
        if (null == instance) {
            instance = new ThreadUtil();
            instance.initThreadUtil();
        }
        return instance;
    }

    public ExecutorService getPool() {
        return executorService;
    }
}