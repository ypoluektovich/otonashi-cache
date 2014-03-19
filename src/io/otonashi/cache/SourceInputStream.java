package io.otonashi.cache;

import java.io.InputStream;

public final class SourceInputStream extends InputStream {

    private final ContentSource source;

    SourceInputStream(ContentSource source) {
        this.source = source;
    }

    @Override
    public int read() throws ClosedSourceException, SourceReadException {
        return source.read();
    }

    @Override
    public int read(byte[] b) throws ClosedSourceException, BadBufferException, SourceReadException {
        return source.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws ClosedSourceException, BadBufferException, SourceReadException {
        return source.read(b, off, len);
    }

}
