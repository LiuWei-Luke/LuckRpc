package com.luckyluke.luckRpc.netty;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.luckyluke.luckRpc.netty.FutureState.DOING;
import static com.luckyluke.luckRpc.netty.FutureState.DONE;

@Slf4j
public class DefaultResponseFuture implements FutureResponse {
    protected final Object lock = new Object();
    protected volatile FutureState futureState = DOING;
    protected Object value = null;
    protected Exception exception = null;
    protected int timeout = 0;
    protected long processTime = 0;
    protected byte rpcVersion;

    protected RpcRequest request;

    protected List<FutureListener> listeners;

    public DefaultResponseFuture(RpcRequest request) {
        this.request = request;
    }

    @Override
    public void onSuccess(Response response) {
        this.value = response.getValue();

        done();
    }

    @Override
    public void onFailure(Response response) {

    }

    @Override
    public long getCreateTime() {
        return 0;
    }

    @Override
    public void setReturnType(Class<?> clazz) {

    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean isCancel() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object getValue() {
        synchronized (lock) {
            for (;;) {
                if (!isDoing()) {
                    break;
                }
            }
            return value;
        }
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public long getProcessTime() {
        return 0;
    }

    @Override
    public void setProcessTime() {

    }

    @Override
    public void setResponseId() {
    }

    @Override
    public long getResponseId() {
        return this.request.getRequestId();
    }

    @Override
    public void addListener(FutureListener futureListener) {
        if (futureListener == null) {
            throw new NullPointerException("Listener is null.");
        }

        boolean noticeNow = false;

        synchronized (lock) {
            if (!isDoing()) {
                noticeNow = true;
            } else {
                if (this.listeners == null) {
                    this.listeners = new ArrayList<>();
                }

                this.listeners.add(futureListener);
            }
        }

        if (noticeNow) {
            noticeListener(futureListener);
        }
    }

    protected void noticeListeners() {
        if (listeners != null) {
            for (FutureListener listener : listeners) {
                noticeListener(listener);
            }
        }

    }

    /**
     * 通知监听者
     * @param listener
     */
    protected void noticeListener(FutureListener listener) {
        try {
            listener.operationComplete(this);
        } catch (Exception e) {
            log.warn("{} Listener {} error: {}",
                    this.getClass().getName() ,listener.getClass().getName(), e);
        }
    }

    private boolean isDoing() {
        return futureState.isDOING();
    }

    protected boolean done() {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }

            futureState = DONE;
            lock.notifyAll();
        }

        return true;
    }

}
