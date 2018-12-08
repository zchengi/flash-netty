package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.GroupMessageResponsePacket;
import com.cheng.the.flash.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/8 20:26
 */
@Slf4j
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) {

        String fromGroupId = groupMessageResponsePacket.getFromGroupId();
        Session fromUser = groupMessageResponsePacket.getFromUser();
        String message = groupMessageResponsePacket.getMessage().trim();
        log.info("收到群 [{}] 中 [{}] 发来的消息: {}", fromGroupId, fromUser, message);
    }
}

