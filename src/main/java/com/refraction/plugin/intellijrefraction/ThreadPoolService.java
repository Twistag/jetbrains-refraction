package com.refraction.plugin.intellijrefraction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolService {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void postJob(final Runnable runnable) {
        executorService.submit(runnable);
    }
}
