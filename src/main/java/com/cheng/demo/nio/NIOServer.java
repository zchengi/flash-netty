package com.cheng.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * JDK原生 NIO 的 API 实现一个简单的服务端通信程序
 * <p>
 * 客户端使用IO实现即可
 *
 * @author cheng
 *         2018/12/5 15:35
 */
public class NIOServer {

    /**
     * 1. NIO 模型中通常会有两个线程，每个线程绑定一个轮询器 selector ，在我们这个例子中 serverSelector 负责轮询是否有新的连接，
     * clientSelector 负责轮询连接是否有数据可读。
     * 2. 服务端监测到新的连接之后，不再创建一个新的线程，而是直接将新连接绑定到 clientSelector 上，
     * 这样就不用 IO 模型中 1w 个 while 循环在死等，参见 1。
     * 3. clientSelector 被一个 while 死循环包裹着，如果在某一时刻有多条连接有数据可读，
     * 那么通过 clientSelector.select(1) 方法可以轮询出来，进而批量处理，参见 2。
     * 4. 数据的读写面向 Buffer，参见 3。
     */
    public static void main(String[] args) throws IOException {

        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(() -> {

            try {
                // 对应IO编程中服务端启动
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                listenerChannel.socket().bind(new InetSocketAddress(8000));
                listenerChannel.configureBlocking(false);
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true) {
                    // 检测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> set = serverSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            if (key.isAcceptable()) {
                                try {
                                    // 1. 每来一个新连接，不需要创建一个线程，而是直接注册到 clientSelector
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    keyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();

        new Thread(() -> {

            try {
                while (true) {
                    // 2. 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> set = clientSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    // (3. 面向 Buffer
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer)
                                            .toString());
                                } finally {
                                    keyIterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
