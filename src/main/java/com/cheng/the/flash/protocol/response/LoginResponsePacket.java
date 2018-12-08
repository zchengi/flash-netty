package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.LOGIN_RESPONSE;

/**
 * 客户端登录返回数据包
 *
 * @author cheng
 *         2018/12/6 13:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponsePacket extends Packet {

    private String userId;

    private String username;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
