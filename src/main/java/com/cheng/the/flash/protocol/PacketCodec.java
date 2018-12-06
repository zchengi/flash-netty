package com.cheng.the.flash.protocol;

import com.cheng.the.flash.protocol.request.LoginRequestPacket;
import com.cheng.the.flash.serialize.Serializer;
import com.cheng.the.flash.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.cheng.the.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * @author cheng
 *         2018/12/6 11:39
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    /**
     * 魔术: 识别出数据包是否遵循自定义协议
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    /**
     * Key：指令
     * value：Java对象类型
     */
    private final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;

    /**
     * key：序列化算法标识
     * value：序列化对象
     */
    private final Map<Byte, Serializer> SERIALIZER_MAP;

    private PacketCodec() {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(LOGIN_REQUEST, LoginRequestPacket.class);

        SERIALIZER_MAP = new HashMap<>();
        JSONSerializer serializer = new JSONSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {

        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER); // 魔数
        byteBuf.writeByte(packet.getVersion()); // 版本号
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // 序列化算法
        byteBuf.writeByte(packet.getCommand()); // 指令
        byteBuf.writeInt(bytes.length); // 数据部分长度
        byteBuf.writeBytes(bytes); // 数据部分

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {

        // 跳过 magic_number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        // 数据包
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return PACKET_TYPE_MAP.get(command);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return SERIALIZER_MAP.get(serializeAlgorithm);
    }
}