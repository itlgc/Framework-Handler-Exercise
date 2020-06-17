package com.it.handle_lib;


import com.it.handle_lib.handle.CusHandler;
import com.it.handle_lib.handle.CusLooper;
import com.it.handle_lib.handle.CusMessage;

/**
 * 手写简版Handler消息机制  消息传递
 * Created by lgc on 2020-02-29.
 */
public class CusActivityThread {

    private CusHandler handle;

    public static void main(String[] args) {
        //创建全局唯一的Looper对象
        CusLooper.prepare();

        //接收消息
        final CusHandler handler = new CusHandler() {
            @Override
            public void handleMessage(CusMessage msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        System.out.println(msg.obj);
                        break;
                    default:

                        break;
                }
            }
        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                CusMessage msg = new CusMessage();
                msg.what = 1;
                msg.obj = "我是自定义的handler消息，你收到了吗？";

                //发送消息
                handler.sendMessage(msg);
            }
        }).start();

        //轮询消息
        CusLooper.loop();
    }

}
