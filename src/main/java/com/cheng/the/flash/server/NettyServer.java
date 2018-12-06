package com.cheng.the.flash.server;

import com.cheng.test.netty.channeloutboundhandler.inbound.InBoundHandlerA;
import com.cheng.test.netty.channeloutboundhandler.inbound.InBoundHandlerB;
import com.cheng.test.netty.channeloutboundhandler.inbound.InBoundHandlerC;
import com.cheng.test.netty.channeloutboundhandler.outbound.OutBoundHandlerA;
import com.cheng.test.netty.channeloutboundhandler.outbound.OutBoundHandlerB;
import com.cheng.test.netty.channeloutboundhandler.outbound.OutBoundHandlerC;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;

/**
 * @author cheng
 *         2018/12/5 19:14
 */
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new ServerHandler());

                        // inBound，处理读数据的逻辑链
                        ch.pipeline().addLast(new InBoundHandlerA());
                        ch.pipeline().addLast(new InBoundHandlerB());
                        ch.pipeline().addLast(new InBoundHandlerC());

                        // outBound，处理写数据的逻辑链
                        ch.pipeline().addLast(new OutBoundHandlerA());
                        ch.pipeline().addLast(new OutBoundHandlerB());
                        ch.pipeline().addLast(new OutBoundHandlerC());
                    }
                });

        bind(serverBootstrap, PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {

        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(LocalDateTime.now() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println(LocalDateTime.now() + ": 端口[" + port + "]绑定失败!");
            }
        });
    }
}
