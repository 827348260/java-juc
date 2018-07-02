package com.chzero.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-07-02 0:26
 * @email 827348260@qq.com
 * @description Fork/Join框架, 分支/合并
 *
 * RecursiveTask 有返回值
 * RecursiveAction 无返回值
 */
public class TestForkJoinPool{
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinSumCalculate(0L, 1000000000L);
        Instant start = Instant.now();
        Long invoke = forkJoinPool.invoke(forkJoinTask);
        Instant end = Instant.now();
        System.out.println(invoke);
        System.out.println(Duration.between(start, end).toMillis());
    }

}


class ForkJoinSumCalculate extends RecursiveTask<Long>{

    private long start;
    private long end;

    private static final long THURSHOLD = 10000L; //临界值

    public ForkJoinSumCalculate(long start, long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute(){
        long len = end - start;
        if (len <= THURSHOLD){
            long sum = 0L;
            for (long i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        }else{
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            left.fork();
            right.fork();
            return left.join() + right.join();
        }
    }
}


