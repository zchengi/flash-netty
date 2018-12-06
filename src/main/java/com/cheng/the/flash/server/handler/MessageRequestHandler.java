package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.MessageRequestPacket;
import com.cheng.the.flash.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @author cheng
 *         2018/12/6 20:20
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(LocalDateTime.now() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
