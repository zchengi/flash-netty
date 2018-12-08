package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.JOIN_GROUP_REQUEST;

/**
 * @author cheng
 *         2018/12/8 18:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
