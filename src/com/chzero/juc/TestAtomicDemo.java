package com.chzero.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 2:04
 * @email 827348260@qq.com
 * @description AtomicXXX 原子变量
 *
 * CAS (Compare-And-Swap(比较和交换)) 是一种硬件对并发的支持，针对多处理器操作而设计的处理器中的一种特殊指令，用于管理对共享数据的并发访问。
 *  CAS 是一种无锁的非阻塞算法的实现。
 *  CAS 包含了 3 个操作数：
 *  需要读写的内存值 V
 *  进行比较的值 A
 *  拟写入的新值 B
 *  当且仅当 V 的值等于 A 时，CAS 通过原子方式用新值 B 来更新 V 的值，否则不会执行任何操作。
 */
public class TestAtomicDemo{

    public static void main(String[] args){
        AtomicDemo atomicDemo = new AtomicDemo();

        for (int i = 1; i <= 10; i++){
            new Thread(atomicDemo, "Atomic - " + i).start();
        }
    }
}


class AtomicDemo implements Runnable{

    private AtomicInteger number = new AtomicInteger();

    @Override
    public void run(){
        try{
            Thread.sleep(20);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " : " + this.getSerialNumber());
    }

    public int getSerialNumber(){
        return this.number.getAndIncrement();
    }
}
