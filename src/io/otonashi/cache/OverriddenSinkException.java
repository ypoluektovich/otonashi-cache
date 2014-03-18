package io.otonashi.cache;

public class OverriddenSinkException extends ClosedSinkException {

    public OverriddenSinkException() {
        super("content sink has been overridden");
    }

    public OverriddenSinkException(StorageIOException suppress) {
        super("content sink has been overridden");
        addSuppressed(suppress);
    }

}
