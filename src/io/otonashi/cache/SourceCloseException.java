package io.otonashi.cache;

public class SourceCloseException extends StorageIOException {

    public SourceCloseException(Throwable cause) {
        super(cause, "content source close failed");
    }

}
