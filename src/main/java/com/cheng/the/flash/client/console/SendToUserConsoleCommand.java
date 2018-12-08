package com.cheng.the.flash.client.console;

import com.cheng.the.flash.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/8 15:44
 */
public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("发送消息给某个用户: ");
        String toUserId = scanner.next();
        String message = scanner.nextLine();

        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
