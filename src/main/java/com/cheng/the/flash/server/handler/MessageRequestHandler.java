package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.MessageRequestPacket;
import com.cheng.the.flash.protocol.response.MessageResponsePacket;
import com.cheng.the.flash.session.Session;
import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/6 20:20
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {

        // 1. 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2. 通过消息发送方的会话信息构造要发送的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUsername(session.getUsername());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3. 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4. 将消息发送给消息接收方
        if (null != toUserChannel && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.error("[{}] 不在线，发送失败", messageRequestPacket.getToUserId());
            // todo 消息发送失败，提示发送方对方不在线
        }
    }
}
