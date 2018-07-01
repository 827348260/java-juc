package com.chzero.juc;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-06-30 16:54
 * @email 827348260@qq.com
 * @description volatile关键字
 *
 * (1)Synchronized：保证可见性和原子性
 * Synchronized能够实现原子性和可见性；
 * 在Java内存模型中，synchronized规定，线程在加锁时，
 * 先清空工作内存→在主内存中拷贝最新变量的副本到工作内存→执行完代码→将更改后的共享变量的值刷新到主内存中→释放互斥锁。
 *
 * (2)Volatile：保证可见性，但不保证操作的原子性
 * Volatile实现内存可见性是通过store和load指令完成的；也就是对volatile变量执行写操作时，会在写操作后加入一条store指令，
 * 即强迫线程将最新的值刷新到主内存中；而在读操作时，会加入一条load指令，即强迫从主内存中读入变量的值。
 * 但volatile不保证volatile变量的原子性
 *
 * (3)Synchronized和Volatile的比较
 * 1）Synchronized保证内存可见性和操作的原子性
 * 2）Volatile只能保证内存可见性
 * 3）Volatile不需要加锁，比Synchronized更轻量级，并不会阻塞线程（volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。）
 * 4）volatile标记的变量不会被编译器优化,而synchronized标记的变量可以被编译器优化（如编译器重排序的优化）.
 * 5）volatile是变量修饰符，仅能用于变量，而synchronized是一个方法或块的修饰符。
 * volatile本质是在告诉JVM当前变量在寄存器中的值是不确定的，使用前，需要先从主存中读取，因此可以实现可见性。而对n=n+1,n++等操作时，
 * volatile关键字将失效，不能起到像synchronized一样的线程同步（原子性）的效果
 */
public class TestVolatile{

    public static void main(String[] args){
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while (true){
/*            synchronized (threadDemo){
                if (threadDemo.isTrue()){
                    System.out.println("---------------------------");
                    break;
                }
            }*/
            if (threadDemo.isTrue()){
                System.out.println("---------------------------");
                break;
            }
        }
    }

}


class ThreadDemo implements Runnable{

    private volatile boolean flag = false;

    @Override
    public void run(){
        try{
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.flag = true;
    }

    public boolean isTrue(){
        return flag;
    }
}
