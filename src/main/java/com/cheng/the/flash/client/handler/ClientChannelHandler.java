package com.cheng.the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端流量监控 未实现
 *
 * @author cheng
 *         2018/12/7 19:34
 */
@Slf4j
public class ClientChannelHandler extends SimpleChannelInboundHandler {

    private static GlobalChannelTrafficShapingHandler TRAFFIC_SHAPING_HANDLER;

    public ClientChannelHandler(NioEventLoopGroup workerGroup) {
        TRAFFIC_SHAPING_HANDLER = new GlobalChannelTrafficShapingHandler(workerGroup);
        init();
    }

    private void init() {
        new Thread(() -> {
            while (true) {
                if (TRAFFIC_SHAPING_HANDLER == null) {
                    break;
                }
                TrafficCounter trafficCounter = TRAFFIC_SHAPING_HANDLER.trafficCounter();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final long totalRead = trafficCounter.cumulativeReadBytes();
                final long totalWrite = trafficCounter.cumulativeWrittenBytes();

                log.info("total read: {} KB", totalRead >> 10);
                log.info("total write: {} KB", totalWrite >> 10);
                log.info("流量监控: \n\r {} ", trafficCounter);
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    }
}
