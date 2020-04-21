package com.luckyluke.luckRpc.zookeeper;

public class ServiceNode extends AbstractZookeeperNode {
    public ServiceNode(Class<?> serviceImp, String serviceName, String address, int port) {
        super(serviceImp, serviceName, address, port);
    }

}
