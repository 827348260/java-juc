package com.chzero.thread;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 0:40
 * @email 827348260@qq.com
 * @description 经典多线程问题: 生产者和消费者 v1
 */
public class ProductCustomer{

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Thread productThread = new Thread(new Productor(clerk));
        Thread consumerThread = new Thread(new Consumer(clerk));
        productThread.start();
        consumerThread.start();
    }


}


class Clerk{  //售货员
    private int product = 0;

    public synchronized void addProduct(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if (product >= 20){
            try{
                wait();

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }else{
            product++;
            System.out.println("生产者生产了第" + product + "个产品");
            notifyAll();
        }
    }

    public synchronized void getProduct(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if (this.product <= 0){
            try{
                wait();
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("消费者取走了第" + product + "个产品");
            product--;
            notifyAll();
        }
    }
}


class Productor implements Runnable{  //生产者
    Clerk clerk;

    public Productor(Clerk clerk){
        this.clerk = clerk;
    }

    public void run(){
        System.out.println("生产者开始生产产品");
        while (true){
            try{
                Thread.sleep((int)Math.random() * 1000);
            }catch (InterruptedException e){
            }
            clerk.addProduct();
        }
    }
}


class Consumer implements Runnable{  //消费者
    Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    public void run(){
        System.out.println("消费者开始取走产品");
        while (true){
            try{
                Thread.sleep((int)Math.random() * 1000);
            }catch (InterruptedException e){
            }
            clerk.getProduct();
        }
    }
}


