package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.cheng.the.flash.protocol.command.Command.*;

/**
 * @author cheng
 *         2018/12/11 23:01
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {

        handlerMap = new HashMap<>();

        // 单聊消息请求处理器
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        // 登出请求处理器
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
        // 创建群请求处理器
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        // 获取群成员请求处理器
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        // 加群请求处理器
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        // 退群请求处理器
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        // 消息群发请求处理器
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
