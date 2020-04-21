package com.luckyluke.luckRpc.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <pre>
 *     单例模式（懒汉式）的Zookeeper注册中心，使用内部类来进行延迟加载
 * </pre>
 */
public class ZkRegisterCenter implements RegisterCenter {
    private static final Logger log = LoggerFactory.getLogger(ZkRegisterCenter.class);
    private static ZkRegisterCenter instance;
    private static CuratorFramework client;
    protected static String zkAddress;
    protected static int timeout = 3000;
    protected static NodeEncoder encoder;
    /**
     *   重试机制
     */
    private RetryPolicy retryPolicy;

    private ZkRegisterCenter() {
        retryPolicy = new ExponentialBackoffRetry(1000, 3);
    }


    private static final class InnerStaticClass {
        public static ZkRegisterCenter zkRegisterCenter = new ZkRegisterCenter();
    }

    public static ZkRegisterCenter getInstance() {
        return InnerStaticClass.zkRegisterCenter;
    }

    @Override
    public Object getNode(String path) throws Exception {
        if (isConnected()) {
            return new String(client.getData().forPath(path));
        }

        return null;
    }

    /**
     * 设置节点
     * @param path
     * @param nodeData
     * @return
     */
    @Override
    public boolean setNode(String path, ServiceNode nodeData) throws Exception {
        if (isConnected()) {
            if (!nodeExists(path)) {
                log.info("创建节点:" + path);
                client.create()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(path, encoder.encode(nodeData));
                return true;
            }
        }

        return false;
    }

    public Stat queryStat(String path) throws InterruptedException, KeeperException {
        return null;
    }

    protected void connect() {
        if (zkAddress == null) {
            throw new NullPointerException("请设置Zookeeper地址");
        }

        if (client == null) {
            client = CuratorFrameworkFactory.builder()
                    .connectString(zkAddress)
                    .connectionTimeoutMs(timeout)
                    .sessionTimeoutMs(timeout * 30)
                    .retryPolicy(retryPolicy)
                    .build();
        }

        client.start();
    }


    protected boolean isConnected() {
        if (client != null) {
            return client.getState() == CuratorFrameworkState.STARTED;
        }

        return false;
    }

    public static String getZkAddress() {
        return zkAddress;
    }

    public static void setZkAddress(String zkAddress) {
        ZkRegisterCenter.zkAddress = zkAddress;
    }

    public static int getTimeout() {
        return timeout;
    }

    public static void setTimeout(int timeout) {
        ZkRegisterCenter.timeout = timeout;
    }

    @Override
    public boolean nodeExists(String path) throws Exception  {
        if (!isConnected()) {
            connect();
        }

        Stat stat = client.checkExists().forPath(path);
        boolean isExists = stat != null;
        System.out.println("节点" + path + "是否存在:" + isExists);
        return isExists;
    }

    public static void setEncoder(NodeEncoder encoder) {
        ZkRegisterCenter.encoder = encoder;
    }


}
