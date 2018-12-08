package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/8 19:19
 */
@Slf4j
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket listGroupMembersResponsePacket) {
        log.info("群 [{}] 中的人: {}", listGroupMembersResponsePacket.getGroupId(), listGroupMembersResponsePacket.getSessionList());
    }
}
