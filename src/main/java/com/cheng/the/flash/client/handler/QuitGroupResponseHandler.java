package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/8 18:37
 */
@Slf4j
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) {

        if (quitGroupResponsePacket.isSuccess()) {
            log.info("退出群 [{}] 成功!", quitGroupResponsePacket.getGroupId());
        } else {
            log.error("退出群 [{}] 失败，原因为: {}",
                    quitGroupResponsePacket.getGroupId(), quitGroupResponsePacket.getReason());
        }
    }
}
