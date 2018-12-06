package com.cheng.the.flash.client;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.protocol.PacketCodec;
import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author cheng
 *         2018/12/6 12:36
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println(LocalDateTime.now() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("cheng");
        loginRequestPacket.setPassword("pwd");

        // 编码
        ByteBuf buffer = PacketCodec.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {

            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(LocalDateTime.now() + ": 客户端登录成功!");
            } else {
                System.out.println(LocalDateTime.now() + ": 客户端登录失败，原因: " + loginResponsePacket.getReason());
            }
        }
    }
}
