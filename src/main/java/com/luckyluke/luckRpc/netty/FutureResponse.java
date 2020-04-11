package com.luckyluke.luckRpc.netty;

public interface FutureResponse extends Future, Response {
    void onSuccess(Response response);
    void onFailure(Response response);
    long getCreateTime();
    void setReturnType(Class<?> clazz);
}
