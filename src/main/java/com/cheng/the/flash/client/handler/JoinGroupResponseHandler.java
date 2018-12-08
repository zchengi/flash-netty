package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/8 18:31
 */
@Slf4j
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) {

        if (joinGroupResponsePacket.isSuccess()) {
            log.info("加入群 [{}] 成功!", joinGroupResponsePacket.getGroupId());
        } else {
            log.error("加入群 [{}] 失败，原因为: {}",
                    joinGroupResponsePacket.getGroupId(), joinGroupResponsePacket.getReason());
        }
    }
}
