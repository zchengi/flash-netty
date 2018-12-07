package com.cheng.the.flash.util;

import com.cheng.the.flash.attribute.Attributes;
import com.cheng.the.flash.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 用户会话信息工具类
 *
 * @author cheng
 *         2018/12/7 22:17
 */
public class SessionUtil {

    /**
     * userId -> channel 映射
     */
    private static final Map<String, Channel> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>();

    private SessionUtil() {}

    public static void bindSession(Session session, Channel channel) {
        USER_ID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            USER_ID_CHANNEL_MAP.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return USER_ID_CHANNEL_MAP.get(userId);
    }
}
