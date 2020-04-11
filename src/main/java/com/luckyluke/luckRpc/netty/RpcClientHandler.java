package com.luckyluke.luckRpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private Client client;
    private MessageHandler messageHandler;

    public RpcClientHandler(Client client, MessageHandler messageHandler) {
        this.client = client;
        this.messageHandler = messageHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        processResponse(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void processResponse(RpcResponse response) {
        this.messageHandler.handle(this.client, response);
    }

}
