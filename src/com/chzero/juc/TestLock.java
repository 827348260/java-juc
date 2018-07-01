package com.chzero.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 16:53
 * @email 827348260@qq.com
 * @description 用于解决多线程安全问题的方式:
 *
 * synchronized: 隐式锁
 * 1. 同步代码块
 * 2. 同步方法
 *
 * 显示锁: 用过lock()方法上锁. 通过unlock()方法释放锁.
 * 3. 同步锁 Lock
 */
public class TestLock{

    public static void main(String[] args){
        Ticket ticket = new Ticket();

        new Thread(ticket, "Window - 1").start();
        new Thread(ticket, "Window - 2").start();
        new Thread(ticket, "Window - 3").start();
    }
}


class Ticket implements Runnable{

    private int ticket = 10;

    private Lock lock = new ReentrantLock();

    @Override
    public void run(){
        while (true){
            this.lock.lock();
            try{
                if (this.ticket > 0){
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 出票成功, 余票[" + --this.ticket + "]");
                }else {
                    break;
                }
            }finally{
                System.out.println("---");
                this.lock.unlock();
            }
        }
    }
}
