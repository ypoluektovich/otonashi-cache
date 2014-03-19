package io.otonashi.cache;

public abstract class ContentSource<H extends StoredDataHandle> implements AutoCloseable {

    protected final SourceInfo<H> sourceInfo;

    public ContentSource(SourceInfo<H> sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    protected abstract boolean isClosed();

    private void checkSourceStateBeforeRead() throws ClosedSourceException {
        if (isClosed()) {
            throw new ClosedSourceException();
        }
    }

    public final synchronized int read() throws ClosedSourceException, SourceReadException {
        checkSourceStateBeforeRead();
        return doRead();
    }

    protected abstract int doRead() throws SourceReadException;

    public final int read(byte[] buf) throws ClosedSourceException, BadBufferException, SourceReadException {
        return read(buf, 0, buf.length);
    }

    public final synchronized int read(byte[] buf, int offset, int length) throws ClosedSourceException, BadBufferException, SourceReadException {
        checkSourceStateBeforeRead();
        BadBufferException.checkBufferParameters(buf, offset, length);
        return doRead(buf, offset, length);
    }

    protected abstract int doRead(byte[] buf, int offset, int length) throws SourceReadException;

    public final SourceInputStream getInputStream() {
        return new SourceInputStream(this);
    }

    @Override
    public final synchronized void close() throws SourceCloseException {
        if (isClosed()) {
            return;
        }
        try {
            doClose();
        } finally {
            sourceInfo.decInterest();
        }
    }

    protected abstract void doClose() throws SourceCloseException;

}
