package com.clear.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自己测试使用的
 */
public class Handler extends ChannelInboundHandlerAdapter {
    @Override
    //读取消息
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(msg);
        //写入并发送消息到客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好数据已经收到!!!!!!!!",CharsetUtil.UTF_8));
    }

    @Override
    //处理异常
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
