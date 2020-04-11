package com.luckyluke.luckRpc.netty;

import com.luckyluke.luckRpc.proxy.ServerProxy;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest o) throws Exception {
        String ip = this.getRemoteIp(ctx);
        log.info("Receive request from: {}, requestId: {}", ip, o.getRequestId());
        RpcResponse rpcResponse = invoke(o);
        // 写入RpcResponse
        this.sendResponse(ctx, rpcResponse);
    }

    private RpcResponse invoke(RpcRequest rpcRequest) throws Exception {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        String serviceName = rpcRequest.getInterfaceName();
        String methodName = rpcRequest.getMethodName();
        Object[] args = rpcRequest.getParameters();
        Class<?>[] paramTypes = rpcRequest.getParameterTypes();

        // 获取服务代理
        ServerProxy serverProxy = ServerProxy.getInstance();
        try {
            Object result = serverProxy.invoke(serviceName, methodName, paramTypes, args);
            rpcResponse.setValue(result);
            return rpcResponse;
        } catch (Exception e){
            rpcResponse.setException(e);
            return rpcResponse;
        }
    }

    /**
     * Get remote ip address from channel
     * @param ctx Channel
     * @return remote ip address
     */
    private String getRemoteIp(ChannelHandlerContext ctx) {
        String ip = "";
        SocketAddress address = ctx.channel().remoteAddress();
        try {
            ip = ((InetSocketAddress) address).getAddress().getHostAddress();
        } catch (Exception e) {
            log.warn("Get remote ip error: {}", e.getMessage());
        }
        return ip;
    }

    /**
     * Send response
     * @param ctx Channel
     * @param rpcResponse rpcResponse
     */
    private void sendResponse(ChannelHandlerContext ctx, RpcResponse rpcResponse) {
        if (ctx.channel().isActive()) {
            ctx.writeAndFlush(rpcResponse).addListeners(ChannelFutureListener.CLOSE);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
