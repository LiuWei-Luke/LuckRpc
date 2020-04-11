package com.luckyluke.luckRpc.proxy;

import com.luckyluke.luckRpc.service.ServiceFind;
import com.luckyluke.luckRpc.service.IHelloService;
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
public class ServerProxyTest {
    @Autowired
    ServiceFind serviceFind;

    @Autowired
    ApplicationContext context;

    @Before
    public void setApplicationContext() {
        serviceFind.setApplicationContext(this.context);
        ConcurrentHashMap<String, Class<?>> hashMap = serviceFind.getHandlerMap();
        assertFalse("服务发现失败",  hashMap.isEmpty());
        ServerProxy.getInstance().setServiceFind(serviceFind);
    }


    @Test
    public void invoke() throws Exception {
        ServerProxy proxy = ServerProxy.getInstance();

        Class<?> service = IHelloService.class;
        String serviceName = service.getName();
        Class<?>[] types = new Class[]{String.class};
        Object[] args = new Object[]{"liuwie"};
        proxy.invoke(serviceName, "sayHello", types, args);
        return;
    }
}