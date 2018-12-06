package com.cheng.the.flash.server;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.protocol.PacketCodec;
import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.request.MessageRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import com.cheng.the.flash.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * @author cheng
 *         2018/12/6 12:46
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        // 判断是否登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            System.out.println(LocalDateTime.now() + ": 收到客户端登录请求……");

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
                System.out.println(LocalDateTime.now() + ": 登录失败!");
            }

            // 登录响应
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {

            // 客户端发来消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(LocalDateTime.now() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(LocalDateTime.now() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        // TODO 校验流程
        return true;
    }
}
