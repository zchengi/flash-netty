package com.cheng.the.flash.protocol.response;

import com.cheng.the.flash.protocol.Packet;

import static com.cheng.the.flash.protocol.command.Command.HEARTBEAT_RESPONSE;

/**
 * @author cheng
 *         2018/12/12 20:35
 */
public class HeartbeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
