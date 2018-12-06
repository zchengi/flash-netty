package com.cheng.the.flash.serialize;

import com.cheng.the.flash.serialize.impl.JSONSerializer;

/**
 * 序列化接口
 *
 * @author cheng
 *         2018/12/6 11:31
 */
public interface Serializer {

    /**
     * 默认 JSON 序列化实现
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * Java 对象转换为二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换为 Java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
