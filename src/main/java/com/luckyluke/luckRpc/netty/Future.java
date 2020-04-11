package com.luckyluke.luckRpc.netty;

public interface Future {
    boolean cancel();

    boolean isCancel();

    boolean isSuccess();

    boolean isDone();

    Object getValue();

    Exception getException();

    void addListener(FutureListener futureListener);
}
