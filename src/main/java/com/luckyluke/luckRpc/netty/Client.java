package com.luckyluke.luckRpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    String host;
    Integer port;
    RpcResponse rpcResponse;

    public Client(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public RpcResponse request(RpcRequest request) {
        this.sendRequest(request);
        return this.rpcResponse;
    }

    public void sendRequest(RpcRequest request) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncoder(RpcRequest.class))
                                    .addLast(new RpcDecoder(RpcResponse.class))
                                    .addLast(new RpcClientHandler(Client.this, new MessageHandler() {
                                        @Override
                                        public void handle(Client client, Object object) {
                                            client.rpcResponse = (RpcResponse) object;
                                        }

                                        @Override
                                        public void handle(Server server, Object object) {

                                        }
                                    }));

                        }
                    });
            try {
                ChannelFuture future = bootstrap.connect(host, port).sync();
                Channel channel = future.channel();
                channel.writeAndFlush(request).sync();
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
