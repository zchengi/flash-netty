package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

/**
 * @author cheng
 *         2018/12/8 18:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
