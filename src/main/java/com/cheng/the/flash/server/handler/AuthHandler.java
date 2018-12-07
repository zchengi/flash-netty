package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * 身份校验
 *
 * @author cheng
 *         2018/12/7 21:20
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 删除当前 handler
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println(LocalDateTime.now() + ": 当前连接登录验证完毕，无需再次验证，AuthHandler 被移除");
        } else {
            System.out.println(LocalDateTime.now() + ": 无登录验证，强制关闭连接!");
        }
    }
}
