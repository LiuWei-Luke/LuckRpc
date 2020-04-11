package com.luckyluke.luckRpc.netty;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {
    Class<?> targetClass;
    Kryo kryo = new Kryo();

    public RpcDecoder(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);

        Input input = new Input(data);
        Object o = kryo.readClassAndObject(input);
        Class<?> a = o.getClass();

        if (targetClass.isInstance(o)) {
            list.add(o);
        }
    }
}
