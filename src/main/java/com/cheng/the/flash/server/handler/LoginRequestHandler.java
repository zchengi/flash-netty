package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import com.cheng.the.flash.session.Session;
import com.cheng.the.flash.util.IdUtil;
import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cheng
 *         2018/12/6 20:20
 */
@Slf4j
// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    /**
     * 构造单例
     */
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private static final AtomicInteger CONNECT_COUNT = new AtomicInteger(0);

    static {
        new Thread(() -> {
            while (true) {
                log.info("当前客户端连接数: {}", CONNECT_COUNT);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })/*.start()*/;
    }

    private LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {

        long startTime = System.currentTimeMillis();

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());

        // 登录校验
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IdUtil.randomId();
            loginResponsePacket.setUserId(userId);

            // 登录成功绑定到 session
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());

            log.info("[{}] 登录成功!", loginRequestPacket.getUsername());
        } else {
            loginResponsePacket.setReason("登录校验失败");
            loginResponsePacket.setSuccess(false);
            log.error("登录失败!");
        }

        // 登录响应
        ctx.writeAndFlush(loginResponsePacket).addListener(future -> {
            if (future.isDone()) {
                // 计算执行完毕时间
                log.info("登录耗时: {} ms", System.currentTimeMillis() - startTime);
            }
        });

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        CONNECT_COUNT.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        CONNECT_COUNT.decrementAndGet();

        // 用户断线后取消绑定
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        // TODO 校验流程
        return true;
    }
}
