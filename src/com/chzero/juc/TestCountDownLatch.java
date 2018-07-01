package com.chzero.juc;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 15:09
 * @email 827348260@qq.com
 * @description CountDownLatch(闭锁) 允许一个或多个线程等待其他线程完成操作
 */
public class TestCountDownLatch{

    public static void main(String[] args) throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(10);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);

        long start = Instant.now().getEpochSecond();
        for (int i = 0; i < 10; i++){
            new Thread(latchDemo).start();
        }
        countDownLatch.await();
        long end = Instant.now().getEpochSecond();
        System.out.println(start + " - " + end);
    }
}


class LatchDemo implements Runnable{

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run(){
        for (int i = 0; i < 1; i++){ System.out.println(i); }
        this.latch.countDown();
    }
}
