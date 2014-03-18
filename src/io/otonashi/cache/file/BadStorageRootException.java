package io.otonashi.cache.file;

import io.otonashi.cache.StorageIOException;

public final class BadStorageRootException extends StorageIOException {

    public static final String NULL = "null storage root";

    public static final String NOT_EXISTS_AND_DIRECTORY = "failed to create storage root, or it is not a directory";

    public static final String NOT_WRITABLE = "storage root is not writable";

    BadStorageRootException(String message) {
        super(message);
    }

    BadStorageRootException(Throwable cause, String message) {
        super(cause, message);
    }

}
