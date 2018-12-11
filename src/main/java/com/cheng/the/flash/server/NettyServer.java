package com.cheng.the.flash.server;

import com.cheng.the.flash.codec.PacketCodecHandler;
import com.cheng.the.flash.codec.Spliter;
import com.cheng.the.flash.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/5 19:14
 */
@Slf4j
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

                        ch.pipeline().addLast(new Spliter());

                        // 单例模式，多个 channel 共享同一个 handler

                        // 解码编码处理器
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);

                        // 登录请求处理器
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        // 身份验证处理器
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        // 单聊消息请求处理器
                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
                        // 创建群请求处理器
                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                        // 获取群成员请求处理器
                        ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
                        // 消息群发请求处理器
                        ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
                        // 加群请求处理器
                        ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
                        // 退群请求处理器
                        ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
                        // 登出请求处理器
                        ch.pipeline().addLast(LogoutRequestHandler.INSTANCE);
                    }
                });

        bind(serverBootstrap, PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {

        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("端口 [{}] 绑定成功!", port);
            } else {
                log.error("端口 [{}] 绑定失败!", port);
            }
        });
    }
}
