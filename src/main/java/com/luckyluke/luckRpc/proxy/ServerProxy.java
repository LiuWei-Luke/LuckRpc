package com.luckyluke.luckRpc.proxy;

import com.luckyluke.luckRpc.service.ServiceFind;
import com.luckyluke.luckRpc.exception.NullServiceException;

import java.lang.reflect.Method;

/**
 * 单例模式的服务代理
 */
public class ServerProxy {
    private ServiceFind serviceFind;

    private ServerProxy() {
    }

    private static ServerProxy serverProxy = new ServerProxy();

    public static ServerProxy getInstance() {
        return serverProxy;
    }

    public Object invoke(String serviceString, String methodName, Class<?>[] paramTypes, Object[] arguments) throws Exception {
        if (this.serviceFind == null) {
            throw new NullServiceException("请先进行服务发现");
        }

        Class<?> service = this.serviceFind.getServiceInterface(serviceString);

        if (service == null) {
            throw new NullServiceException("该服务不存在:" +  serviceString);
        }

        Method method = service.getMethod(methodName, paramTypes);
        if (method == null) {
            throw new NullPointerException("该方法不存在");
        }
        return method.invoke(service.getDeclaredConstructor().newInstance(), arguments);
    }

    public void setServiceFind(ServiceFind serviceFind) {
        this.serviceFind = serviceFind;
    }
}
