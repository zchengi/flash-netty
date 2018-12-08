package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * 发消息对象
 *
 * @author cheng
 *         2018/12/6 16:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUsername;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
