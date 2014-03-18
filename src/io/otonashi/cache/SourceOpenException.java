package io.otonashi.cache;

public class SourceOpenException extends StorageIOException {

    public SourceOpenException(Throwable cause, String messageTemplate, Object... messageParams) {
        super(cause, messageTemplate, messageParams);
    }

}
