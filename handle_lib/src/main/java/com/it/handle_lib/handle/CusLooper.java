package com.it.handle_lib.handle;

/**
 * 自定义Looper
 * Created by lgc on 2020-02-29.
 */
public class CusLooper {

    private static final ThreadLocal<CusLooper> sThreadLocal = new ThreadLocal<>();
    public  CusMessageQueue messageQueue;

    //创建全局唯一主线程 Looper对象
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new CusLooper());
    }

    //私有构造，保证唯一性
    private CusLooper(){
        messageQueue = new CusMessageQueue();
    }


    public static CusLooper myLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        //从全局ThreadLocalMap中获取唯一 looper对象
        CusLooper me = myLooper();
        CusMessageQueue queue = me.messageQueue;

        //轮询消息，存在消息发送出去
        while (true){
            //取消息
            CusMessage msg = queue.next();
            if (msg == null) {
                return;
            }
            //消息分发
            msg.target.dispatchMessage(msg);
        }

    }
}
