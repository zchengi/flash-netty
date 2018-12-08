package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.cheng.the.flash.protocol.command.Command.GROUP_MESSAGE_REQUEST;

/**
 * @author cheng
 *         2018/12/8 20:14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;

    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
