package com.cheng.the.flash.client.console;

import com.cheng.the.flash.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/8 20:25
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("发送消息给某个群组: ");
        String toGroupId = scanner.next();
        String message = scanner.nextLine();

        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));
    }
}
