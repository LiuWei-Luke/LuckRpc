package com.luckyluke.luckRpc.zookeeper;

import com.luckyluke.luckRpc.service.HelloService;
import com.luckyluke.luckRpc.service.IHelloService;
import org.apache.zookeeper.KeeperException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

public class ZkRegisterCenterTest {
    ZkRegisterCenter zkRegisterCenter;

    @Before
    public void init() throws Exception {
        ZkRegisterCenter.setTimeout(4000);
        ZkRegisterCenter.setZkAddress("127.0.0.1:2181");
        zkRegisterCenter = ZkRegisterCenter.getInstance();
        zkRegisterCenter.connect();
        ZkRegisterCenter.setEncoder(new NodeEncoder(ServiceNode.class));
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getNode() throws Exception {
        String path = "/test";
        ServiceNode node = new ServiceNode(HelloService.class, IHelloService.class.getName(), "127.0.0.1", 8080);
        boolean t = zkRegisterCenter.setNode(path, node);
        System.out.println("注册服务结果: " + t);

        String nodeData = (String) zkRegisterCenter.getNode(path);
        System.out.println("获取节点数据成功：" + nodeData);
        Assert.assertNotNull(nodeData);
    }

    @Test
    public void isConnected() {
    }
}