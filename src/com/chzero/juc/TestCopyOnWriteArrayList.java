package com.chzero.juc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-01 14:39
 * @email 827348260@qq.com
 * @description  CopyOnWriteArrayList/CopyOnWriteArraySet "写入并复制"
 * 适合多线程迭代, 添加操作多时, 效率低, 因为每次添加时都会进行一次复制, 开销会非常大
 */
public class TestCopyOnWriteArrayList{

    public static void main(String[] args){

        for (int i = 0 ; i < 10 ; i++){
            new Thread(new HelloThread()).start();
        }

    }

}

class HelloThread implements Runnable{

    //此种方式存在并发修改异常
    //private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
    }

    @Override
    public void run(){

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            list.add("X");
        }
    }
}



