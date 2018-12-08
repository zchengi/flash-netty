package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.session.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.cheng.the.flash.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * @author cheng
 *         2018/12/8 18:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListGroupMembersResponsePacket extends Packet {

    public List<Session> sessionList;
    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}