package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * 用户登出返回数据包
 *
 * @author cheng
 *         2018/12/8 16:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
