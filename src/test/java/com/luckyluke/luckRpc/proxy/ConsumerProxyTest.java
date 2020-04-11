package com.luckyluke.luckRpc.proxy;

import com.luckyluke.luckRpc.netty.Client;
import com.luckyluke.luckRpc.service.IHelloService;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConsumerProxyTest {

    @Test
    public void getProxy() {

    }

    @Test
    public void invoke() {
        Client client = new Client("127.0.0.1", 8088);
        ConsumerProxy<IHelloService> consumerProxy = new ConsumerProxy<>(IHelloService.class, client);
        IHelloService helloService = consumerProxy.getProxy();
        Object o = helloService.sayHello("liuwei");
        System.out.println(o);
    }
}