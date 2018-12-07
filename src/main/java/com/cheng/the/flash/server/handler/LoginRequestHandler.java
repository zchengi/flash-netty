package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import com.cheng.the.flash.session.Session;
import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cheng
 *         2018/12/6 20:20
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final AtomicInteger connectCount = new AtomicInteger(0);

    static {
        new Thread(() -> {
            while (true) {
                System.out.println(LocalDateTime.now() + ": 当前客户端连接数: " + connectCount);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })/*.start()*/;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());

        // 登录校验
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);

            // 登录成功绑定到 session
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());

            System.out.println(LocalDateTime.now() + ": [" + loginRequestPacket.getUsername() + "] 登录成功!");

        } else {
            loginResponsePacket.setReason("登录校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(LocalDateTime.now() + ": 登录失败!");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        connectCount.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        connectCount.decrementAndGet();

        // 用户断线后取消绑定
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        // TODO 校验流程
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
