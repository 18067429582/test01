package com.clear.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 客户端启动类
 */
public class SimpleChatClient {

    public static void main(String[] args) throws Exception{
        new SimpleChatClient("localhost", 8899).run();
    }

    private final String host;
    private final int port;
    public SimpleChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            //连接到远程节点，等待连接完成，channel是获得的通道
            Channel channel = bootstrap.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放线程池资源
            group.shutdownGracefully();
        }
    }
}

