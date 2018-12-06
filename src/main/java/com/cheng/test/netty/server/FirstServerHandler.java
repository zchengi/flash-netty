package com.cheng.test.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * 数据处理逻辑实现
 *
 * @author cheng
 *         2018/12/5 19:17
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println(LocalDateTime.now() + ": 服务端主动向客户端推送消息");

        // 1. 获取数据
        ByteBuf buffer = getByteBuf(ctx, "这是服务端主动发送来的消息");

        // 2. 写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        // 收到客户端数据
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(LocalDateTime.now() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("UTF-8")));

        // 回复数据到客户端
        System.out.println(LocalDateTime.now() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx, "你好，欢迎访问博客: note.chengix.com");
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg) {

        byte[] bytes = msg.getBytes(Charset.forName("UTF-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
