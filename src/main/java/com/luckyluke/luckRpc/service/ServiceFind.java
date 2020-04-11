package com.luckyluke.luckRpc.service;

import com.luckyluke.luckRpc.exception.NullServiceException;
import com.luckyluke.luckRpc.service.RpcService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lw
 * 用于发现被注解为Rpc的服务
 */
@Slf4j
@Service
public class ServiceFind {
    private ConcurrentHashMap<String, Class<?>> handlerMap;

    public ServiceFind() {
        handlerMap = new ConcurrentHashMap<>();
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (!serviceBeanMap.isEmpty()) {
            for (Object bean: serviceBeanMap.values()) {
                RpcService annotation = bean.getClass().getAnnotation(RpcService.class);
                Class<?> serviceImp = bean.getClass();
                String serviceName = annotation.values().getName();
                log.debug("Service {}", serviceName);
                /*
                String serviceVersion = annotation.version();
                if (!serviceVersion.isEmpty()) {
                   serviceName = serviceName + "-" + serviceVersion;
                }
                 */
                handlerMap.put(serviceName, serviceImp);
            }
        }
    }

    public Class<?> getServiceInterface(String serviceInterface) {
        Class<?> service = this.handlerMap.get(serviceInterface);
        if (service == null) {
            throw new NullServiceException("该服务不存在:" +  serviceInterface);
        }

        return service;
    }

    public ConcurrentHashMap<String, Class<?>> getHandlerMap() {
        return handlerMap;
    }

    public boolean register(Class<?> serviceInterface, Class<?> serviceImp) throws Exception {
        String serviceName = serviceInterface.getName();
        if(this.handlerMap.containsKey(serviceName)) {
            throw new Exception("已存在该名称的服务，请先注销服务");
        }

        this.handlerMap.put(serviceInterface.getName(), serviceImp);
        System.out.println("Service has registered: " + serviceInterface.getName());
        return true;
    }

    public boolean logoff(Class<?> serviceInterface) {
        if (!this.handlerMap.containsKey(serviceInterface.getName())) {
            return true;
        }

        this.handlerMap.remove(serviceInterface.getName());
        return true;
    }
}
