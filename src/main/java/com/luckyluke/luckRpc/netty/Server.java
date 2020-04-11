package com.luckyluke.luckRpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class Server {
    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup worker;
    private NioEventLoopGroup boss;
    private Integer port;
    private String host;

    public Server(String host, Integer port) {
        this.serverBootstrap = new ServerBootstrap();
        this.worker = new NioEventLoopGroup();
        this.boss = new NioEventLoopGroup();
        this.port = port;
        this.host = host;
    }

     public void startServer() throws InterruptedException {
        log.info("Netty server start at {}:{}", host, port);
        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new RpcDecoder(RpcRequest.class));
                            socketChannel.pipeline().addLast(new RpcEncoder(RpcResponse.class));
                            socketChannel.pipeline().addLast(new RpcServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(host, this.port).sync();
            // 关闭rpc服务器
            future.channel().closeFuture().sync();
        } finally {
            this.worker.shutdownGracefully();
            this.boss.shutdownGracefully();
        }
     }
}
