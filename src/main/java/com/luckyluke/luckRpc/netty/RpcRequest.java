package com.luckyluke.luckRpc.netty;

import java.io.Serializable;

public class RpcRequest implements Serializable {
    private Object[] parameters;
    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private String desc;
    private byte rpcVersion;
    private long requestId;

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte getRpcVersion() {
        return rpcVersion;
    }

    public void setRpcVersion(byte rpcVersion) {
        this.rpcVersion = rpcVersion;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
