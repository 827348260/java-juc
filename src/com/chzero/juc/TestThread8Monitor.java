package com.chzero.juc;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 22:07
 * @email 827348260@qq.com
 * @description 线程八锁 题目
 */
public class TestThread8Monitor{

    public static void main(String[] args){

        //1. 输出的是One 还是 Two
        Number number = new Number();
        Number number2 = new Number();
        //new Thread(number::getOne).start();
        new Thread(new Runnable(){
            @Override
            public void run(){
                number.getOne();
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run(){
                number2.getTwo();
            }
        }).start();
        //new Thread(number::getTwo).start();
        //new Thread(number2::getTwo).start();
        //new Thread(number::getThree).start();

    }
}

class Number{

    public static synchronized void getOne(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("One");
    }

    public static synchronized void getTwo(){
        System.out.println("Two");
    }

    public void getThree(){
        System.out.println("Three");
    }

}
