package io.otonashi.cache;

public class SinkWriteException extends StorageIOException {

    public SinkWriteException(Throwable cause) {
        super(cause, "write to content sink failed");
    }

}
