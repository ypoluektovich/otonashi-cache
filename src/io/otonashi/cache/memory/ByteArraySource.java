package io.otonashi.cache.memory;

import io.otonashi.cache.ContentSource;
import io.otonashi.cache.SourceCloseException;
import io.otonashi.cache.SourceInfo;

import java.io.ByteArrayInputStream;

final class ByteArraySource extends ContentSource<ByteArrayHandle> {

    private ByteArrayInputStream stream;

    ByteArraySource(SourceInfo<ByteArrayHandle> sourceInfo) {
        super(sourceInfo);
        this.stream = new ByteArrayInputStream(sourceInfo.handle.data);
    }

    @Override
    protected boolean isClosed() {
        return stream == null;
    }

    @Override
    protected int doRead() {
        return stream.read();
    }

    @Override
    protected int doRead(byte[] buf, int offset, int length) {
        return stream.read(buf, offset, length);
    }

    @Override
    protected void doClose() throws SourceCloseException {
        stream = null;
    }

}
