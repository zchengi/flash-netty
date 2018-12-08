package com.cheng.the.flash.util;

import java.util.UUID;

/**
 * @author cheng
 *         2018/12/8 15:09
 */
public class IdUtil {

    private IdUtil() {
    }

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
