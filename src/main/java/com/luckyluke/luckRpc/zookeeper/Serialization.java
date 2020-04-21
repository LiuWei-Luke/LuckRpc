package com.luckyluke.luckRpc.zookeeper;

public interface Serialization {
    byte[] encode(Object node);
    Object decode(byte[] byteBuf);
}
