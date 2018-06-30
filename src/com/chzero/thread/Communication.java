package com.chzero.thread;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 0:34
 * @email 827348260@qq.com
 * @description 使用Object类的wait(), notify()/notifyAll()实现两个线程交替打印数字
 */
public class Communication implements Runnable{
    int i = 1;

    public void run(){
        while (true){
            synchronized (this){
                notify(); //唤醒另外一个等待线程
                if (i <= 100){
                    System.out.println(Thread.currentThread().getName() + ":" + i++);
                }else{
                    break;
                }
                //当前线程执行完毕, 进入等待状态. 释放锁和CPU资源
                try{ wait(); }catch (InterruptedException e){}
            }
        }
    }

    public static void main(String[] args){

        Communication communication = new Communication();
        Thread thread1 = new Thread(communication, "T-1");
        Thread thread2 = new Thread(communication, "T-2");
        thread1.start();
        thread2.start();
    }
}

