package com.cheng.the.flash.attribute;

import io.netty.util.AttributeKey;

/**
 * @author cheng
 *         2018/12/6 16:07
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
