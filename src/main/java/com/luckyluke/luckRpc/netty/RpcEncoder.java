package com.luckyluke.luckRpc.netty;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

public class RpcEncoder extends MessageToByteEncoder<Object> {
    private Class<?> targetClass;

    Kryo kryo;

    public RpcEncoder(Class<?> targetClass) {
        this.targetClass = targetClass;
        kryo = new Kryo();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
        if (targetClass.isInstance(o)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream, 4096);
            kryo.writeClassAndObject(output, o);
            output.flush();
            byte[] byteArr = byteArrayOutputStream.toByteArray();
            byteBuf.writeInt(byteArr.length);
            byteBuf.writeBytes(byteArr);
        }
    }
}
