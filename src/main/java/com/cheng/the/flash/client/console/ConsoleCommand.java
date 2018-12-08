package com.cheng.the.flash.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/8 12:42
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
