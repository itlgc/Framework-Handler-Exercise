### 手写简化版Handler消息机制流程



#### ThreadLocal工作原理

ThreadLocal 是一个线程内部的数据存储类。不同的线程访问同一个ThreadLocal，不管是调用它的set还是get方法，它们对ThreadLocal的读写操作仅限于各自线程内部

在Handler中用它来存储Looper来保证它在线程中的唯一性

```java
//ThreadLocal 线程内部的存储类
    final ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Nullable
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
```

日志打印：

> 主线程threadLocal：我是重写初始化返回值
> thread-1：我是重写初始化返回值
> thread-1：我是thread1设置后的值
> thread-2：我是重写初始化返回值
> thread-2：我是thread2设置后的值



#### Android消息机制模型

模型的解释：

1.以Handler的sendMessage方法为例，当发送一个消息后，会将此消息加入消息队列MessageQueue中。

2.Looper负责去遍历消息队列并且将队列中的消息分发给对应的Handler进行处理。

3.在Handler的handleMessage方法中处理该消息，这就完成了一个消息的发送和处理过程。



具体仿写实现见代码。









