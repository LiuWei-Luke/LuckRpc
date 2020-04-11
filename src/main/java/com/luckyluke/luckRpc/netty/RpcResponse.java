package com.luckyluke.luckRpc.netty;

import java.io.Serializable;

public class RpcResponse implements Serializable {
    private Object value;
    private long requestId;
    private Exception exception;
    private int timeout;
    private long processTime;
    private byte rpcVersion;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public byte getRpcVersion() {
        return rpcVersion;
    }

    public void setRpcVersion(byte rpcVersion) {
        this.rpcVersion = rpcVersion;
    }
}
