package io.otonashi.cache.memory;

import io.otonashi.cache.ContentSource;
import io.otonashi.cache.ContentStorage;
import io.otonashi.cache.SinkOpenException;
import io.otonashi.cache.SourceInfo;
import io.otonashi.cache.SourceOpenException;

public final class MemoryStorage extends ContentStorage<ByteArrayHandle, ByteArrayHandle> {

    public MemoryStorage() {
        super(null);
    }

    @Override
    protected ByteArrayStreamSink createSink(Object key) throws SinkOpenException {
        return new ByteArrayStreamSink(key, this);
    }

    @Override
    protected ByteArrayHandle getOutputHandle(ByteArrayHandle handle) {
        return handle;
    }

    @Override
    protected ContentSource<ByteArrayHandle> createSource(SourceInfo<ByteArrayHandle> sourceInfo) throws SourceOpenException {
        return new ByteArraySource(sourceInfo);
    }

}
