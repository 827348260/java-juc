package com.chzero.juc;

import java.util.concurrent.*;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 23:29
 * @email 827348260@qq.com
 * @description
 */
public class TestScheduledThreadPool{

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledFuture<String> future = scheduledExecutorService.schedule((Callable<String>)() -> "-----", 3, TimeUnit.SECONDS);
        System.out.println(future.get());
        scheduledExecutorService.shutdown();
    }

}
