package com.it.handle_lib;


/**
 *
 * ThreadLocal类的学习
 * ThreadLocal作用:不同的线程访问同一个ThreadLocal，不管是调用它的set还是get方法，它们对ThreadLocal的读写操作仅限于各自线程内部
 * 在Handler中用它来存储Looper来保证它在线程中的唯一性
 * Created by lgc on 2020-02-29.
 */
public class CcbThreadLocalTest {

    public static void main(String[] args) {
        //ThreadLocal 线程内部的存储类
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
            @Override
            protected String initialValue() {
                //重写初始化方法，默认返回null
                return "我是重写初始化返回值";
            }
        };
        System.out.println("主线程threadLocal：" + threadLocal.get());


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String value1 = threadLocal.get();
                System.out.println("thread-1：" + value1);

                //数据存储以后，只有再指定线程中可以获取到存储的数据，对于其他线程来说则无法获取到数据。
                threadLocal.set("我是thread1设置后的值");
                String value2 = threadLocal.get();
                System.out.println("thread-1：" + value2);

                //避免占用大量无意义的内存占用
                threadLocal.remove();

            }
        });
        thread1.start();



        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String value1 = threadLocal.get();
                System.out.println("thread-2：" + value1);

                threadLocal.set("我是thread2设置后的值");
                String value2 = threadLocal.get();
                System.out.println("thread-2：" + value2);

                //避免占用大量无意义的内存占用
                threadLocal.remove();

            }
        });
        thread2.start();


    }

}
