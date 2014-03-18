package io.otonashi.cache;

public class ClosedSinkException extends StorageIOException {

    public ClosedSinkException() {
        super("content sink is not open for writing");
    }

    ClosedSinkException(String message) {
        super(message);
    }

}
