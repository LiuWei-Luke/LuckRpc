package com.luckyluke.luckRpc.netty;

public interface Response {
    /**
     *
     * <pre>
     *     如果 request 正常处理，那么会返回 Object value，而如果 request 处理有异常，那么 getValue 会抛出异常
     * </pre>
     * @return
     * @throws RuntimeException
     */
    Object getValue();

    Exception getException();

    long getProcessTime();

    void setProcessTime();

    void setResponseId();

    long getResponseId();
}
