package com.chzero.thread;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 0:18
 * @email 827348260@qq.com
 * @description 单例设计模式之懒汉式
 */
public class Singleton{

    private static Singleton instance = null;

    private Singleton(){}

    public static Singleton getInstance(){
        if (instance == null){
            synchronized (Singleton.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}


class TestSingleton{
    public static void main(String[] args){
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println(singleton1 == singleton2);
    }
}

