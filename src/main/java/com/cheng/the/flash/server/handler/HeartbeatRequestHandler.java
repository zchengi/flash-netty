package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.HeartbeatRequestPacket;
import com.cheng.the.flash.protocol.response.HeartbeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author cheng
 *         2018/12/12 20:45
 */
@ChannelHandler.Sharable
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequestPacket> {

    public static final HeartbeatRequestHandler INSTANCE = new HeartbeatRequestHandler();

    private HeartbeatRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartbeatRequestPacket msg) {
        ctx.writeAndFlush(new HeartbeatResponsePacket());
    }
}
