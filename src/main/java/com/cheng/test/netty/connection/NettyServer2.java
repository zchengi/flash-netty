package com.cheng.test.netty.connection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *
 * @author cheng
 *         2018/12/5 17:21
 */
public class NettyServer2 {
    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                    }
                });

        // 自动绑定递增端口
        bind(serverBootstrap, 1000);

        // 服务端启动其他方法
//        serverBootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
//            @Override
//            protected void initChannel(NioSocketChannel ch) throws Exception {
//                System.out.println("服务端启动中");
//            }
//        });

        // attr() 方法
//        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");

        // childAttr() 方法
//        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        // childOption() 方法
//        serverBootstrap
//                .childOption(ChannelOption.SO_KEEPALIVE, true)
//                .childOption(ChannelOption.TCP_NODELAY, true);

        // option() 方法
//        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
