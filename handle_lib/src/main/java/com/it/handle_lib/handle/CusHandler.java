package com.it.handle_lib.handle;

/**
 * 自定义Handler
 * Created by lgc on 2020-02-29.
 */
public class CusHandler {

    private CusMessageQueue messageQueue;

    public CusHandler(){
        //从全局线程中取出Looper
        CusLooper looper = CusLooper.myLooper();
        if (looper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        messageQueue = looper.messageQueue;

    }



    public void sendMessage(CusMessage msg) {
        enqueueMessage(msg);

    }

    private void enqueueMessage(CusMessage msg) {
        //赋值当前消息
        msg.target = this;
        //将消息传入队列中
        messageQueue.enqueueMessage(msg);
    }

    public void dispatchMessage(CusMessage msg) {
        handleMessage(msg);
    }


    public void handleMessage(CusMessage msg) {

    }
}
