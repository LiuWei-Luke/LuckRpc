package com.luckyluke.luckRpc.zookeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public class MyZookeeperConfig {
    private String zkAddress;
    private int timeout;

    public MyZookeeperConfig(String zkAddress, int timeout) {
        this.zkAddress = zkAddress;
        this.timeout = timeout;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
