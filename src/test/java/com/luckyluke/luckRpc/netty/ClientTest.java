package com.luckyluke.luckRpc.netty;

import com.luckyluke.luckRpc.service.IHelloService;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ClientTest {
    private Client client = new Client("127.0.0.1", 8088);

    @Test
    public void setupClient() throws InterruptedException, NoSuchMethodException {
        RpcRequest req = new RpcRequest();

        Class<?> service = IHelloService.class;
        String serviceName = service.getName();
        Class<?>[] types = new Class[]{String.class};
        Object[] args = new Object[]{"liuwie"};
        req.setInterfaceName(serviceName);
        req.setParameterTypes(types);
        req.setParameters(args);
        Method m = service.getMethod("sayHello", String.class);
        req.setMethodName(m.getName());
        RpcResponse rpcResponse = client.request(req);
        System.out.println(rpcResponse);
    }
}