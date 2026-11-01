package com.zly.tliaswebmanagement;

/*
ThreadLocal:
ThreadLocal并不是一个Thread,而是Thread的局部变量。
ThreadLocal为每个线程提供一份单独的存储空间，具有线程隔离的效果 不同线程之间不会互相干扰

ThreadLocal常用方法：
public void set(T value)设置当前线程的线程局部变量的值
public T get()返回当前线程所对应的线程局部变量的值
public void remove()移除当前线程的线程局部变量
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {
        local.set("Main Message");

        // 创建线程
        new Thread(new Runnable() {
            private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
            @Override
            public void run() {
                // 演示线程隔离
                threadLocal.set("ThreadLocal Message");
                System.out.println(threadLocal.get());
                System.out.println(local.get()); // 是null
            }
        }).start();

        System.out.println(Thread.currentThread().getName() + ":" + local.get());
        local.remove();
        System.out.println(Thread.currentThread().getName() + ":" + local.get());


    }
}
