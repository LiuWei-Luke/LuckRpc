package com.luckyluke.luckRpc.netty;

public enum FutureState {
    DONE(1),
    DOING(0),
    CANCEL(2);

    public final int value;

    private FutureState(int value) {
        this.value = value;
    }

    public boolean isDone() {
        return this == DONE;
    }

    public boolean isDOING() {
        return this == DOING;
    }

    public boolean isCANCEL() {
        return this == CANCEL;
    }
}
