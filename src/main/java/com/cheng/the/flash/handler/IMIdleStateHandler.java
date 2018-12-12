package com.cheng.the.flash.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 空闲检测
 *
 * @author cheng
 *         2018/12/12 20:22
 */
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READ_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        super(READ_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info(READ_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
