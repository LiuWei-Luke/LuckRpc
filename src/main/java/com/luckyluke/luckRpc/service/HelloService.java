package com.luckyluke.luckRpc.service;

import org.springframework.stereotype.Service;


@Service
@RpcService(values = IHelloService.class, version = "1.0")
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String s) {
        return String.format("Hello: %s", s);
    }
}
