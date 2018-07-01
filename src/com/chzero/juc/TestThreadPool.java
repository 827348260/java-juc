package com.chzero.juc;

import java.util.concurrent.*;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 22:47
 * @email 827348260@qq.com
 * @description 1. 线程池: 提供了一个线程队列, 队列中保存着所有等待状态的线程. 避免线程的创建于销毁开销, 提高的响应速度;
 * 2. 线程池的体系机构:
 * java.util.concurrent.Executor: 负责线程的使用和调度的根接口;
 * |--ExecutorService: 子接口: 线程池的主要接口;
 * |--ThreadPoolExecutor 线程池实现类;
 * |--ScheduledExecutorService: 子接口, 负责线程调度的子接口;
 * |--ScheduledThreadPoolExecutor: 线程调度实现类; public class ScheduledThreadPoolExecutor extends ThreadPoolExecutor implements ScheduledExecutorService
 *
 * 强烈建议程序员使用较为方便的 Executors 工厂方法 ：
 *  Executors.newCachedThreadPool()（无界线程池，可以进行自动线程回收）
 *  Executors.newFixedThreadPool(int)（固定大小线程池）
 *  Executors.newSingleThreadExecutor()（单个后台线程）
 */
public class TestThreadPool{

    public static void main(String[] args) throws ExecutionException, InterruptedException{

        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        //2. 为线程池中的线程分配任务
        pool.submit(threadPoolDemo);
        pool.submit(threadPoolDemo);
        pool.submit(threadPoolDemo);
        pool.submit(threadPoolDemo);
        pool.submit(threadPoolDemo);
        pool.submit(threadPoolDemo);
        pool.submit(threadPoolDemo);

        Future<String> submit = pool.submit(new Callable<String>(){
            @Override
            public String call() throws Exception{
                return "String";
            }
        });
        System.out.println(submit.get());


        //3.关闭线程池
        pool.shutdown();


    }

}


class ThreadPoolDemo implements Runnable{

    private int i = 0;

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + " : " + i);
    }
}
