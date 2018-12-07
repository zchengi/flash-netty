package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 身份校验
 *
 * @author cheng
 *         2018/12/7 21:20
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 删除当前 handler
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
