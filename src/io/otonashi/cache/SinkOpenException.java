package io.otonashi.cache;

public class SinkOpenException extends StorageIOException {

    public SinkOpenException(String message) {
        super(message);
    }

    public SinkOpenException(Throwable cause, String messageTemplate, Object... messageParams) {
        super(cause, messageTemplate, messageParams);
    }

}
