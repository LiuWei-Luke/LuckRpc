package com.luckyluke.luckRpc.zookeeper;

import org.apache.zookeeper.KeeperException;

public interface RegisterCenter {
    Object getNode(String path) throws Exception;

    boolean setNode(String path, ServiceNode nodeData) throws Exception;

    boolean nodeExists(String path) throws Exception;
}
