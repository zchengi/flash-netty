package com.cheng.the.flash.codec;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @see PacketCodecHandler
 *
 * @author cheng
 *         2018/12/6 20:36
 */
@Deprecated
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodec.INSTANCE.encode(out, packet);
    }
}
