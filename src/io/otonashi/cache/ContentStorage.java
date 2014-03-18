package io.otonashi.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class ContentStorage<I extends StoredDataHandle, O extends StoredDataHandle> {

    protected final StorageCallback callback;

    protected final ConcurrentMap<Object, ContentSink<I>> sinkByKey = new ConcurrentHashMap<>();

    protected final ConcurrentMap<Object, SourceInfo<O>> sourceInfoByKey = new ConcurrentHashMap<>();

    public ContentStorage(StorageCallback callback) {
        this.callback = callback;
    }

    public final ContentSink getSink(Object key) throws SinkOpenException {
        ContentSink<I> newSink = createSink(key);
        ContentSink<I> oldSink = sinkByKey.put(key, newSink);
        if (oldSink != null) {
            oldSink.override();
        }
        newSink.open();
        return newSink;
    }

    protected abstract ContentSink<I> createSink(Object key) throws SinkOpenException;

    /*
     * Must be synchronized to protect against a newer sink committing itself
     * between conditional remove from sink map and unconditional commit into source info map.
     */
    final synchronized boolean commit(Object key, I inputHandle, ContentSink<I> sink) {
        if (!sinkByKey.remove(key, sink)) {
            return false;
        }
        SourceInfo<O> newInfo = new SourceInfo<>(getOutputHandle(inputHandle));
        SourceInfo<O> oldInfo = sourceInfoByKey.put(key, newInfo);
        if (oldInfo != null) {
            oldInfo.markForRelease();
        }
        return true;
    }

    protected abstract O getOutputHandle(I handle);

    public final ContentSource<O> getSource(Object key) throws SourceOpenException {
        SourceInfo<O> sourceInfo = sourceInfoByKey.get(key);
        return (sourceInfo == null || !sourceInfo.incInterest()) ? null : createSource(sourceInfo);
    }

    protected abstract ContentSource<O> createSource(SourceInfo<O> sourceInfo) throws SourceOpenException;

    public final void expire(Object key) {
        SourceInfo<O> sourceInfo = sourceInfoByKey.remove(key);
        if (sourceInfo != null) {
            sourceInfo.markForRelease();
        }
    }

}
