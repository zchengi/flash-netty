package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.cheng.the.flash.protocol.command.Command.CREATE_GROUP_RESPONSE;

/**
 * @author cheng
 *         2018/12/8 15:06
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> usernameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
