package com.clear.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * descripiton: 服务器初始化
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 15:46
 * @modifier:
 * @since:
 */

/**
 * 初始化容器
 */
public class ServerIniterHandler extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new Handler());
        /*pipeline.addLast("handler", new ServerHandler());*/
        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
    }

}