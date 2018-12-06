package com.cheng.the.flash.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.cheng.the.flash.serialize.Serializer;
import com.cheng.the.flash.serialize.SerializerAlgorithm;

/**
 * @author cheng
 *         2018/12/6 11:33
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
