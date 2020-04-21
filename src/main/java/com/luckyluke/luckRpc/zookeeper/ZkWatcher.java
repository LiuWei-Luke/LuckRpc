package com.luckyluke.luckRpc.zookeeper;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ZkWatcher implements Watcher {
    private final static Logger log = LoggerFactory.getLogger(ZkWatcher.class);

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("{} is watching event: {}", this.getClass().getName(), watchedEvent.getType());
    }
}
