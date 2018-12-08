package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;

import static com.cheng.the.flash.protocol.command.Command.LOGOUT_REQUEST;

/**
 * 用户登出请求数据包
 *
 * @author cheng
 *         2018/12/8 16:17
 */
public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
