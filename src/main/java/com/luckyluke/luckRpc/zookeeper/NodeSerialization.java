package com.luckyluke.luckRpc.zookeeper;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

@Slf4j
public class NodeSerialization implements Serialization {
    protected Class<?> targetClass;
    protected Kryo kryo;

    public NodeSerialization(Class<?> targetClass) {
        kryo = new Kryo();
        this.targetClass = targetClass;
    }

    @Override
    public byte[] encode(Object o) {
        if (targetClass.isInstance(o)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Output output = new Output(outputStream, 4096);
            kryo.writeClassAndObject(output, o);
            output.flush();
            return outputStream.toByteArray();
        }
        return new byte[0];
    }

    @Override
    public Object decode(byte[] byteBuf) {
        Input input = new Input(byteBuf);
        Object o = kryo.readClassAndObject(input);
        if (targetClass.isInstance(o)) {
            return o;
        }
        log.info("Service node class error: {}", o.getClass());
        return null;
    }
}
