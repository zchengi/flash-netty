package com.cheng.the.flash.protocol.command;

import com.cheng.the.flash.protocol.Packet;
import com.cheng.the.flash.protocol.PacketCodec;
import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.serialize.Serializer;
import com.cheng.the.flash.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author cheng
 *         2018/12/6 12:12
 */
public class PacketCodecTest {

    @Test
    public void codecTest() {

        Serializer serializer = new JSONSerializer();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion((byte) 1);
        loginRequestPacket.setUsername("cheng");
        loginRequestPacket.setPassword("zy123456");

        PacketCodec packetCodec = PacketCodec.INSTANCE;
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 编码
        packetCodec.encode(byteBuf, loginRequestPacket);

        //  解码
        Packet decodedPacket = packetCodec.decode(byteBuf);

        assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }
}