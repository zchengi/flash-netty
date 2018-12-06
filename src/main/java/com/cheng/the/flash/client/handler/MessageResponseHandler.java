package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @author cheng
 *         2018/12/6 20:20
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
        System.out.println(LocalDateTime.now() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
    }
}
