package io.otonashi.cache;

public abstract class StoredDataHandle {

    private final StorageCallback callback;

    protected StoredDataHandle(StorageCallback callback) {
        this.callback = callback;
    }

    protected final void release() {
        try {
            doRelease();
        } catch (Throwable e) {
            callback.releaseFailed(e);
        }
    }

    protected abstract void doRelease() throws Throwable;

}
