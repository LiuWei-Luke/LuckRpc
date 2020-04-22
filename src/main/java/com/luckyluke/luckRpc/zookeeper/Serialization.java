package com.luckyluke.luckRpc.zookeeper;

public interface Serialization<T> {
    byte[] encode(T node);
    T decode(byte[] byteBuf);
}
