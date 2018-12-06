package com.cheng.test.netty.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author cheng
 *         2018/12/5 17:48
 */
public class NettyClient2 {

    /**
     * 最大尝试重连次数
     */
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1. 指定线程模型
                .group(workerGroup)
                // 2. 指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3. IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                    }
                });

        // 4. 建立连接
//        bootstrap.connect("www.chengix.com", 80).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println("连接成功!");
//            } else {
//                System.err.println("连接失败!");
//            }
//        });

        // 失败重连
//        connect(bootstrap, "127.0.0.1", 777);

        // 指定最大重试次数
//        connect(bootstrap, "127.0.0.1", 777, MAX_RETRY);

        // attr() 方法
//        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");

        // option() 方法
        bootstrap
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
    }

    private static void connect(Bootstrap bootstrap, String host, int port) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.err.println("连接失败，开始重连!");
                connect(bootstrap, host, port);
            }
        });
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接!");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;

                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");

                bootstrap.config().group().schedule(() ->
                        connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
