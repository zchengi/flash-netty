package com.cheng.the.flash.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 客户端与服务端通信对象的抽象类
 *
 * @author cheng
 *         2018/12/6 11:20
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 指令
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
