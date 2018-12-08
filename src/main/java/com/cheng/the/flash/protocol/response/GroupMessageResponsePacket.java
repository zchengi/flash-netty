package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.session.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

/**
 * @author cheng
 *         2018/12/8 20:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
