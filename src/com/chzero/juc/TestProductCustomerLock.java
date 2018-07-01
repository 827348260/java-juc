package com.chzero.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 17:39
 * @email 827348260@qq.com
 * @description 生产者消费者v2
 *
 * 何为虚假唤醒?
 *
 * 虚假唤醒就是一些obj.wait()会在除了obj.notify()和obj.notifyAll()的其他情况被唤醒,而此时是不应该唤醒的。
 *
 * 解决的办法是基于while来反复判断进入正常操作的临界条件是否满足:
 *
 * synchronized (obj) {
 * while (<condition does not hold>)
 * obj.wait();
 * ... // Perform action appropriate to condition
 * }
 *
 * 如何修复问题?
 *
 * #1.  使用可同步的数据结构来存放数据,比如LinkedBlockingQueue之类。由这些同步的数据结构来完成繁琐的同步操作。
 *
 * #2.  双层的synchronized使用没有意义,保留外层即可。
 *
 * #3.  将if替换为while,解决虚假唤醒的问题
 */
public class TestProductCustomerLock{

    public static void sleep(){
        try{
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Clerk1 clerk = new Clerk1();
        new Thread(new Productor1(clerk)).start();
        new Thread(new Productor1(clerk)).start();
        new Thread(new Consumer1(clerk)).start();
        new Thread(new Consumer1(clerk)).start();

    }
}


//售货员
class Clerk1{

    private int product = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = this.lock.newCondition();

    public void addProduct(){
        this.lock.lock();
        try{
            while (product >= 1){ //为了避免虚假唤醒问题, 应该总是使用在循环中唤醒, 唤醒后再次判断条件
                System.out.println("商品已满.");
                try{
                    System.out.println("***生产者---睡眠");
                    this.condition.await();
                    System.out.println(">>生产者唤醒.");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println("添加第" + ++product + "个产品");
            this.condition.signalAll();
            System.out.println("试图唤醒消费者");
        }finally{
            this.lock.unlock();
        }

    }

    public void getProduct(){
        this.lock.lock();
        try{
            while (this.product <= 0){
                System.out.println("缺货.");
                try{
                    System.out.println("***消费者---睡眠");
                    this.condition.await();
                    System.out.println("--消费者唤醒.");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            System.out.println("售出第" + product-- + "个产品");
            this.condition.signalAll();
            System.out.println("试图唤醒生产者.");
        }finally{
            this.lock.unlock();
        }
    }
}


class Productor1 implements Runnable{  //生产者

    private Clerk1 clerk;

    public Productor1(Clerk1 clerk){
        this.clerk = clerk;
    }

    @Override
    public void run(){
        int i = 20;
        while (i-- > 0){
            //TestProductCustomer.sleep();
            clerk.addProduct();
        }
    }
}


class Consumer1 implements Runnable{  //消费者
    private Clerk1 clerk;

    public Consumer1(Clerk1 clerk){
        this.clerk = clerk;
    }

    @Override
    public void run(){
        int i = 20;
        while (i-- > 0){
            //TestProductCustomer.sleep();
            clerk.getProduct();
        }
    }
}
