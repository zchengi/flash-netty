package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.ListGroupMembersRequestPacket;
import com.cheng.the.flash.protocol.response.ListGroupMembersResponsePacket;
import com.cheng.the.flash.session.Session;
import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 *         2018/12/8 18:45
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket listGroupMembersRequestPacket) {

        // 1. 获取群的 channelGroup
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);

        // 2. 变量群成员的 channel 对应的 session，构造群成员信息
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channels) {
            sessionList.add(SessionUtil.getSession(channel));
        }

        // 3. 构建获取成员列表响应协会客户端
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        listGroupMembersResponsePacket.setGroupId(groupId);
        listGroupMembersResponsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}