package com.clear.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class SimpleChatClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    /**
     * 读写消息
     */
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf s) throws Exception {
        System.out.println("接收到消息："+s.toString(CharsetUtil.UTF_8));
    }

    @Override
    /**
     * 处理异常
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}