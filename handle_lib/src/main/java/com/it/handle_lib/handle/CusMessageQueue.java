package com.it.handle_lib.handle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 自定义MessageQueue
 * Created by lgc on 2020-02-29.
 */
public class CusMessageQueue {


    //阻塞队列
    BlockingQueue<CusMessage> queue = new ArrayBlockingQueue<CusMessage>(50);


    //取消息
    public CusMessage next() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //存入消息
    public void enqueueMessage(CusMessage msg) {
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
