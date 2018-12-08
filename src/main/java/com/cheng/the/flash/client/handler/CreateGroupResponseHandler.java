package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/8 15:17
 */
@Slf4j
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) {
        log.info("群创建成功，id为 [{}]，群成员为: {}",
                createGroupResponsePacket.getGroupId(),
                createGroupResponsePacket.getUsernameList());
    }
}
