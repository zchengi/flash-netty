package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.cheng.the.flash.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * 创建群组数据包
 *
 * @author cheng
 *         2018/12/8 13:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
