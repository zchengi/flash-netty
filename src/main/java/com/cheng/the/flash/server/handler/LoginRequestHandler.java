package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
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
                System.out.println(LocalDateTime.now() + " - 当前客户端连接数: " + connectCount);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {

        System.out.println(LocalDateTime.now() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());

        // 登录校验
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(LocalDateTime.now() + ": 登录成功!");
        } else {
            loginResponsePacket.setReason("登录校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(LocalDateTime.now() + ": 登录失败!");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        // TODO 校验流程
        return true;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        connectCount.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        connectCount.decrementAndGet();
    }
}
