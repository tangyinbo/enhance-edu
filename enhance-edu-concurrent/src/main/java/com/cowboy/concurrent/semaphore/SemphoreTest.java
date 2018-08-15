package com.cowboy.concurrent.semaphore;

import java.util.concurrent.*;

/**
 * java 并发库旗语测试
 *
 * @Auther: Tangyinbo
 * @Date: 2018/7/30 14:07
 * @Description:
 */
public class SemphoreTest {
    static ExecutorService executorService = new ThreadPoolExecutor(3,8,2, TimeUnit.MINUTES,new LinkedBlockingQueue(),Executors.defaultThreadFactory());

    static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

    private Semaphore semaphore;
    //允许并发数目
    private int concurrentCount;


    public SemphoreTest(int concurrentCount) {
        this.concurrentCount = concurrentCount;
        semaphore = new Semaphore(concurrentCount);
    }

    public void release(){
        this.semaphore.release(1);
        System.out.println("availablePermits:"+semaphore.availablePermits());
    }

    public void run(){
        try{
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+"....run");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //semaphore.release();
        }
    }

    public static void main(String[] args) {
        final SemphoreTest test = new SemphoreTest(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("----------relese------------");
                test.release();
            }
        },0,1,TimeUnit.SECONDS);


        for(int i=0;i<10;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    test.run();
                }
            });
        }




    }
}
