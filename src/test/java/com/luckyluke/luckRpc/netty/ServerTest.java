package com.luckyluke.luckRpc.netty;

import com.luckyluke.luckRpc.service.ServiceFind;
import com.luckyluke.luckRpc.proxy.ServerProxy;
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

    @Before
    public void setApplicationContext() {
        serviceFind.setApplicationContext(this.context);
        ConcurrentHashMap<String, Class<?>> hashMap = serviceFind.getHandlerMap();
        assertFalse("服务发现失败",  hashMap.isEmpty());
        ServerProxy.getInstance().setServiceFind(serviceFind);
    }


    @Test
    public void startServer() throws InterruptedException {
        server.startServer();
    }
}