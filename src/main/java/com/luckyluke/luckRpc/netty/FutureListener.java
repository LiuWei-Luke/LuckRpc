package com.luckyluke.luckRpc.netty;

public interface FutureListener {
    void operationComplete(Future future) throws Exception;
}
