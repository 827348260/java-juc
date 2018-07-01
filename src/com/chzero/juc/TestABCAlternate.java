package com.chzero.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 20:26
 * @email 827348260@qq.com
 * @description 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC…… 依次递归
 */
public class TestABCAlternate{

    public static void main(String[] args){
        AlternateDemo alternateDemo = new AlternateDemo();
        new Thread(() -> {
            for (int i = 0; i < 10; i++){
                alternateDemo.loopA(i);
                System.out.println("------------------------------");
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++){
                alternateDemo.loopB(i);
                System.out.println("------------------------------");
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++){
                alternateDemo.loopC(i);
                System.out.println("------------------------------");
            }
        }, "C").start();
    }

}


class AlternateDemo{

    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = this.lock.newCondition();
    private Condition condition2 = this.lock.newCondition();
    private Condition condition3 = this.lock.newCondition();

    public void loopA(int totalLoop){
        this.lock.lock();
        try{
            //1. 判断
            while (this.number != 1){
                this.condition1.await();
            }
            //2. 输出
            for (int i = 0; i < 1; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            this.number = 2;
            this.condition2.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            this.lock.unlock();
        }
    }

    public void loopB(int totalLoop){
        this.lock.lock();
        try{
            //1. 判断
            while (this.number != 2){
                this.condition2.await();
            }
            //2. 输出
            for (int i = 0; i < 1; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            this.number = 3;
            this.condition3.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            this.lock.unlock();
        }
    }

    public void loopC(int totalLoop){
        this.lock.lock();
        try{
            //1. 判断
            while (this.number != 3){
                this.condition3.await();
            }
            //2. 输出
            for (int i = 0; i < 1; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            this.number = 1;
            this.condition1.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            this.lock.unlock();
        }
    }

}
