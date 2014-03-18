package io.otonashi.cache;

import java.io.IOException;

public abstract class StorageIOException extends IOException {

    public StorageIOException(String message) {
        super(message);
    }

    public StorageIOException(String messageTemplate, Object... messageParams) {
        this(String.format(messageTemplate, messageParams));
    }

    public StorageIOException(Throwable cause, String message) {
        super(message, cause);
    }

    public StorageIOException(Throwable cause, String messageTemplate, Object... messageParams) {
        this(cause, String.format(messageTemplate, messageParams));
    }

}
