package io.otonashi.cache;

import java.io.IOException;
import java.io.OutputStream;

public final class SinkOutputStream extends OutputStream {

    private final ContentSink sink;

    SinkOutputStream(ContentSink sink) {
        this.sink = sink;
    }

    @Override
    public void write(int b) throws IOException {
        sink.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        sink.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        sink.write(b, off, len);
    }

}
