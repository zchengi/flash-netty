package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.request.HeartbeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 客户端定时发送心跳给服务端
 *
 * @author cheng
 *         2018/12/12 20:30
 */
public class HeartbeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartbeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartbeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartbeatRequestPacket());
                scheduleSendHeartbeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}