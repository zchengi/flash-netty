package com.cheng.the.flash.client.console;

import com.cheng.the.flash.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/8 16:22
 */
public class LogoutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        channel.writeAndFlush(new LogoutRequestPacket());
    }
}
