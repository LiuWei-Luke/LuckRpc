package com.luckyluke.luckRpc.proxy;

import com.luckyluke.luckRpc.netty.Client;
import com.luckyluke.luckRpc.netty.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConsumerProxy<T> implements InvocationHandler {
    private Class<T> serviceInterface;
    private Client client;

    public ConsumerProxy(Class<T> serviceInterface, Client client) {
        this.serviceInterface = serviceInterface;
        this.client = client;
    }

    public T getProxy() {
        return (T) Proxy.newProxyInstance(this.serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setInterfaceName(this.serviceInterface.getName());
        request.setRequestId(System.currentTimeMillis());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setParameterTypes(method.getParameterTypes());
        Object res = client.request(request).getValue();
        return res;
    }
}
