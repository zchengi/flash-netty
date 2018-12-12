package com.cheng.the.flash.protocol.request;

import com.cheng.the.flash.protocol.Packet;

import static com.cheng.the.flash.protocol.command.Command.HEARTBEAT_REQUEST;

/**
 * @author cheng
 *         2018/12/12 20:33
 */
public class HeartbeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
