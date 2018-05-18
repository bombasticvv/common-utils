package com.utils.part1.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
	
	/**
	 * 1.newFixedThreadPool 
	 * 线程池的创建入口Executors.真正干活的是ExecutorService
	 * 创建固定大小的线程池。每次提交一个任务,就会启一个线程来接客,直到线程池的线程数量达到线程池的上限
	 */
	public static void testFixedThreadPool(){
		ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i = 0; i<5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        executorService.shutdown();
	}
	
	/**
	 * 2.newCachedThreadPool
	 * 创建一个可缓存的线程池。每次提交一个任务,委派给线程池空闲的线程处理, 如果木有空闲的线程, 则直接创建新线程,任务被执行完后,当前线程加入到线程池维护。其生命周期超过一定时间会被销毁回收。
	 */
	public static void testCachedThreadPool(){
		ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i<5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        executorService.shutdown();
	}
	
	/**
	 * 3.newSingleThreadExecutor
	 * 创建只有一个线程的线程池。问题来了, 一个线程的线程池和普通创建一个线程一样么？当然不一样.线程销毁问题。 
	 */
	public static void testSingleThread(){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i = 0; i<5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        executorService.shutdown();
	}
	
	/**
	 * 4.newScheduledThreadPool 
	 * 创建一个大小不受限的线程池。提供定时、周期地执行任务能力。
	 */
	public static void testScheduledThreadPool(){
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        long initialDelay = 1, delay = 1;
        // 应用启动1S后,每隔1S执行一次
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, initialDelay, delay, TimeUnit.SECONDS);
        // 应用启动1S后,每隔2S执行一次
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, initialDelay, delay, TimeUnit.SECONDS);
	}
}
