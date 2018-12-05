package com.cheng.demo.io;

import java.net.Socket;
import java.util.Date;

/**
 * @author cheng
 *         2018/12/5 15:44
 */
public class IOClient {

    /**
     * 客户端的代码相对简单，连接上服务端 8000 端口之后，每隔 2 秒，我们向服务端写一个 带有时间戳的 "hello world"。
     */
    public static void main(String[] args) {

        new Thread(() -> {

            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
        }).start();
    }
}
