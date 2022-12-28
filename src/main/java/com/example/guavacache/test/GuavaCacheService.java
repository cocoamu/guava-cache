package com.example.guavacache.test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 测试get方法 Callable线程安全
 */
public class GuavaCacheService {


    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(3)
            .build();

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println("thread1");
            try {
                String value = cache.get("key", new Callable<String>() {
                    public String call() throws Exception {
                        System.out.println("thread1"); //加载数据线程执行标志
                        Thread.sleep(5000); //模拟加载时间
                        return "thread1";
                    }
                });
                System.out.println("thread1 " + value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println("thread2");
            try {
                String value = cache.get("key", new Callable<String>() {
                    public String call() throws Exception {
                        System.out.println("thread2"); //加载数据线程执行标志
                        Thread.sleep(5000); //模拟加载时间
                        return "thread2";
                    }
                });
                System.out.println("thread2 " + value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }

}