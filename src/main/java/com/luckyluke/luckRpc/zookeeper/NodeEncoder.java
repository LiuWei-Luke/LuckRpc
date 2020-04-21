package com.luckyluke.luckRpc.zookeeper;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

public class NodeEncoder {
    private static final Logger log = LoggerFactory.getLogger(NodeEncoder.class);
    Class<?> targetClass;
    Kryo kryo;

    public NodeEncoder(Class<?> targetClass) {
        this.targetClass = targetClass;
        this.kryo = new Kryo();
    }

    /**
     * 对节点对象进行加密
     * @param o
     * @throws Exception
     */
    protected byte[] encode(Object o) throws Exception{
        if (targetClass.isInstance(o)) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream, 4096);
            kryo.writeClassAndObject(output, o);
            output.flush();
            return stream.toByteArray();
        } else {
            log.warn("序列化对象不符合类型 {}", this.targetClass.getName());
            throw new ClassNotFoundException("序列化对象不符合类型.");
        }
    }
}
