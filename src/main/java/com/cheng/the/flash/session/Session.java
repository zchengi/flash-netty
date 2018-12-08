package com.cheng.the.flash.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cheng
 *         2018/12/7 22:17
 */
@Data
@NoArgsConstructor
public class Session {

    /**
     * 用户唯一标识
     */
    private String userId;

    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString() {
        return userId + ":" + username;
    }
}
