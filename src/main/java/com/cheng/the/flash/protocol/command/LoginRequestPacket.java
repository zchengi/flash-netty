package com.cheng.the.flash.protocol.command;

import lombok.Data;

import static com.cheng.the.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * 客户端登录请求数据包
 *
 * @author cheng
 *         2018/12/6 11:24
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
