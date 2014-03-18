package io.otonashi.cache;

public class SinkCloseException extends StorageIOException {

    public SinkCloseException(Throwable cause) {
        super(cause, "content sink close failed");
    }

    SinkCloseException(Throwable cause, String message) {
        super(cause, message);
    }

}
