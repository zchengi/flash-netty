package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import com.cheng.the.flash.session.Session;
import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/6 20:43
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {

        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();

        if (loginResponsePacket.isSuccess()) {
            log.info("[{}] 登录成功，userId 为: {}", username, userId);
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
        } else {
            log.error("[{}] 登录失败，原因: {}", username, loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.error("客户端连接被关闭!");
    }
}
