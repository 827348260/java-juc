package com.chzero.thread;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 0:21
 * @email 827348260@qq.com
 * @description 死锁  当线程1获取到s1锁的同时 线程2拿到s2锁时, 此时双方都要获取对方持有的锁, 那么产生死锁啦~~
 */
public class TestDeadLock{

    static StringBuffer s1 = new StringBuffer();
    static StringBuffer s2 = new StringBuffer();

    public static void main(String[] args){

        //线程1
        new Thread(){
            @Override
            public void run(){
                synchronized (s1){
                    s2.append("A");
                    synchronized (s2){
                        s1.append("B");
                        System.out.println("1:" + s1);
                        System.out.println("1:" + s2);
                    }
                }
            }
        }.start();

        //线程2
        new Thread(){
            @Override
            public void run(){
                synchronized (s2){
                    s2.append("C");
                    synchronized (s1){
                        s1.append("D");
                        System.out.println("2:" + s2);
                        System.out.println("2:" + s1);
                    }
                }
            }
        }.start();
    }

}

