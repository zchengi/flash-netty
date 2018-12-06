package com.cheng.the.flash.server;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.protocol.PacketCodec;
import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cheng
 *         2018/12/6 12:46
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println(LocalDateTime.now() + ": 客户端开始登录……");

        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        // 判断是否登录请求数据包
        if (packet instanceof LoginRequestPacket) {

            // 登录流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            // 登录校验
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(LocalDateTime.now() + ": 登录成功!");
            } else {
                loginResponsePacket.setReason("登录校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": 登录失败!");
            }

            // 登录响应
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        // TODO 校验流程
        return true;
    }
}
