package com.chzero.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 15:56
 * @email 827348260@qq.com
 * @description
 * 1. 创建执行线程的第三种方式: 实现Callable<V>接口; 与Runnable接口比较, Callable<V>接口可以有返回值和抛出异常;
 * 2. 执行Callable方式, 需要FutureTask实现类的支持, 用于接收运算结果, FutureTask是Future接口的实现类;
 * 3. 如果FutureTask未获取到返回值, 执行此子线程的线程将阻塞;  类似CountDownLatch
 */
public class TestCallable{

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        CallableDemo callableDemo = new CallableDemo();
        FutureTask<String> future = new FutureTask<>(callableDemo);
        new Thread(future).start();
        System.out.println(future.get());
    }
}

class CallableDemo implements Callable<String>{



    @Override
    public String call() throws Exception{
        return "AAAAAAAAA";
    }
}
