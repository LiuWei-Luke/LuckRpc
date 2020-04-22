package com.luckyluke.luckRpc.zookeeper;

import java.io.Serializable;

public abstract class AbstractZookeeperNode implements Serializable {
    Class<?> serviceImp;
    String serviceName;
    String address;
    int port;

    public AbstractZookeeperNode(Class<?> serviceImp, String serviceName, String address, int port) {
        this.serviceImp = serviceImp;
        this.serviceName = serviceName;
        this.address = address;
        this.port = port;
    }

    public AbstractZookeeperNode() {
    }

    public Class<?> getServiceImp() {
        return serviceImp;
    }

    public void setServiceImp(Class<?> serviceImp) {
        this.serviceImp = serviceImp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
