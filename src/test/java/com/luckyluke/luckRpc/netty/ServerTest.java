package com.luckyluke.luckRpc.netty;

import com.luckyluke.luckRpc.service.HelloService;
import com.luckyluke.luckRpc.service.IHelloService;
import com.luckyluke.luckRpc.service.ServiceFind;
import com.luckyluke.luckRpc.proxy.ServerProxy;
import com.luckyluke.luckRpc.zookeeper.ServiceNode;
import com.luckyluke.luckRpc.zookeeper.ZkRegisterCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServerTest {
    @Autowired
    ServiceFind serviceFind;

    @Autowired
    ApplicationContext context;

    private Server server = new Server("127.0.0.1", 8088);

    ZkRegisterCenter zkRegisterCenter;

    @Before
    public void setApplicationContext() throws Exception {
        ZkRegisterCenter.setTimeout(4000);
        ZkRegisterCenter.setZkAddress("127.0.0.1:2181");
        zkRegisterCenter = ZkRegisterCenter.getInstance();
        zkRegisterCenter.connect();


        /**
         * 假装注册服务到zookeeper
         */
        String path = "/test";
        serviceFind.setApplicationContext(this.context);
        ConcurrentHashMap<String, Class<?>> hashMap = serviceFind.getHandlerMap();
        assertFalse("服务发现失败",  hashMap.isEmpty());
        for (String k : hashMap.keySet()) {
            boolean t = zkRegisterCenter.setNode(path, new ServiceNode(hashMap.get(k), k, "127.0.0.1", 8088));
            System.out.println("服务:" + k + "注册结果：" + t);
        }


        ServerProxy.getInstance().setServiceFind(serviceFind);
    }


    @Test
    public void startServer() throws InterruptedException {
        server.startServer();
    }
}