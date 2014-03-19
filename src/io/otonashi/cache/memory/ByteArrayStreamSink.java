package io.otonashi.cache.memory;

import io.otonashi.cache.ContentSink;
import io.otonashi.cache.SinkCloseException;
import io.otonashi.cache.SinkWriteException;

import java.io.ByteArrayOutputStream;

final class ByteArrayStreamSink extends ContentSink<ByteArrayHandle> {

    private ByteArrayOutputStream stream = new ByteArrayOutputStream();

    ByteArrayStreamSink(Object key, MemoryStorage storage) {
        super(key, new ByteArrayHandle(), storage);
    }

    @Override
    protected void doOpen() {
        // do nothing
    }

    @Override
    protected void doWrite(int b) throws SinkWriteException {
        try {
            stream.write(b);
        } catch (OutOfMemoryError e) {
            throw new SinkWriteException(e);
        }
    }

    @Override
    protected void doWrite(byte[] buf, int offset, int length) throws SinkWriteException {
        try {
            stream.write(buf, offset, length);
        } catch (OutOfMemoryError e) {
            throw new SinkWriteException(e);
        }
    }

    @Override
    protected void doClose() throws SinkCloseException {
        try {
            handle.data = stream.toByteArray();
        } catch (OutOfMemoryError e) {
            throw new SinkCloseException(e);
        } finally {
            stream = null;
        }
    }

}
