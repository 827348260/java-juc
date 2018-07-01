package com.chzero.juc;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 21:14
 * @email 827348260@qq.com
 * @description ReadWriteLock 读写锁
 * 写写/读写 需要"互斥"
 * 读读 不需要"互斥"
 */
public class TestReadWriteLock{
    public static void main(String[] args){
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        new Thread(new Runnable(){
            @Override
            public void run(){
                for (; ; ){
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    readWriteLockDemo.write(new Random(1000).nextInt());
                }
            }
        }, "W : ").start();

        for (; ; ){
            try{
                Thread.sleep(800);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            new Thread(new Runnable(){
                @Override
                public void run(){
                    readWriteLockDemo.read();
                }
            }, "R : ").start();
        }
    }
}


class ReadWriteLockDemo{

    private int number = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void read(){ //读
        this.lock.readLock().lock();
        try{
            System.out.println("Read : " + number);
        }finally{
            this.lock.readLock().unlock();
        }
    }

    public void write(int number){ //写
        this.lock.writeLock().lock();
        try{
            System.out.println("Write : " + number);
            this.number = number;
        }finally{
            this.lock.writeLock().unlock();
        }
    }


}
