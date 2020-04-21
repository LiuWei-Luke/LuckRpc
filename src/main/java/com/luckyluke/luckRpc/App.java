package com.luckyluke.luckRpc;

import com.luckyluke.luckRpc.service.ServiceFind;
import com.luckyluke.luckRpc.zookeeper.MyZookeeperConfig;
import com.luckyluke.luckRpc.zookeeper.ZkRegisterCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {
    private ServiceFind serviceFind;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        try {
            ServiceFind serviceFind = new ServiceFind();
            serviceFind.setApplicationContext(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}