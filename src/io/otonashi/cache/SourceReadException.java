package io.otonashi.cache;

public class SourceReadException extends StorageIOException {

    public SourceReadException(Throwable cause) {
        super(cause, "read from content source failed");
    }

}
