package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cheng.the.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * 客户端登录请求数据包
 *
 * @author cheng
 *         2018/12/6 11:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequestPacket extends Packet {

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
