package com.cheng.the.flash.client.handler;

import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.protocol.response.LoginResponsePacket;
import com.cheng.the.flash.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author cheng
 *         2018/12/6 20:43
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println(LocalDateTime.now() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("cheng");
        loginRequestPacket.setPassword("pwd");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {

        if (loginResponsePacket.isSuccess()) {
            System.out.println(LocalDateTime.now() + ": 客户端登录成功");

            // 设置登录状态为 已登录
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(LocalDateTime.now() + ": 客户端登录失败，原因: " + loginResponsePacket.getReason());
        }

    }

}
