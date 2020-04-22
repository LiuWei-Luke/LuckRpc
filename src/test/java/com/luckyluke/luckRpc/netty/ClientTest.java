package com.luckyluke.luckRpc.netty;

import com.luckyluke.luckRpc.service.IHelloService;
import com.luckyluke.luckRpc.zookeeper.ServiceNode;
import com.luckyluke.luckRpc.zookeeper.ZkRegisterCenter;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ClientTest {


    ZkRegisterCenter zkRegisterCenter;

    @Before
    public void init() throws Exception {
        ZkRegisterCenter.setTimeout(4000);
        ZkRegisterCenter.setZkAddress("127.0.0.1:2181");
        zkRegisterCenter = ZkRegisterCenter.getInstance();
        zkRegisterCenter.connect();
    }


    @Test
    public void setupClient() throws Exception {
        ServiceNode serviceNode = zkRegisterCenter.getNode("/test");
        Client client = new Client("127.0.0.1", serviceNode.getPort());
        RpcRequest req = new RpcRequest();

        Class<?> service = serviceNode.getServiceImp();
        String serviceName = serviceNode.getServiceName();
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