package com.luckyluke.luckRpc.service;

import com.luckyluke.luckRpc.service.ServiceFind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceFindTest {

    @Autowired
    ServiceFind serviceFind;

    @Autowired
    ApplicationContext context;

    @Before
    public void setApplicationContext() {
        serviceFind.setApplicationContext(this.context);
        ConcurrentHashMap<String, Class<?>> hashMap = serviceFind.getHandlerMap();
        Assert.assertFalse("服务发现失败",  hashMap.isEmpty());
    }

}