package com.luckyluke.luckRpc.domain;

import lombok.Data;

@Data
public class RpcRequestDTO {
    private String requestId;
    private String interfaceName;
    private String serviceVersion;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
