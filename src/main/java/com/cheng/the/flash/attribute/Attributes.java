package com.cheng.the.flash.attribute;

import com.cheng.the.flash.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author cheng
 *         2018/12/6 16:07
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
