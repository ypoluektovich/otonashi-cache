package io.otonashi.cache;

public class ClosedSourceException extends StorageIOException {

    public ClosedSourceException() {
        super("content source is not open for reading");
    }

}
