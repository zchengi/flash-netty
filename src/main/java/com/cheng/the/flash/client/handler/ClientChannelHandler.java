package com.cheng.the.flash.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;

import java.time.LocalDateTime;

/**
 * 客户端流量监控 未实现
 *
 * @author cheng
 *         2018/12/7 19:34
 */
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

                System.out.println(LocalDateTime.now() + "- total read: " + (totalRead >> 10) + " KB");
                System.out.println(LocalDateTime.now() + "- total write: " + (totalWrite >> 10) + " KB");
                System.out.println(LocalDateTime.now() + "- 流量监控: " + System.lineSeparator() + trafficCounter);
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    }
}
