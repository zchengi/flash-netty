package com.cheng.the.flash.server.handler;

import com.cheng.the.flash.protocol.request.CreateGroupRequestPacket;
import com.cheng.the.flash.protocol.response.CreateGroupResponsePacket;
import com.cheng.the.flash.util.IdUtil;
import com.cheng.the.flash.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 *         2018/12/8 14:58
 */
@Slf4j
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) {

        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2. 筛选出待加入群的用户的 channel 和 username
        List<String> usernameList = new ArrayList<>();
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                usernameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        // 3. 创建群聊结果响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        String groupId = IdUtil.randomId();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUsernameList(usernameList);

        // 4. 给每个客户端发送拉入群的通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);

        log.info("群创建成功，id 为 [{}],群成员为: {}", groupId, usernameList);
    }
}
