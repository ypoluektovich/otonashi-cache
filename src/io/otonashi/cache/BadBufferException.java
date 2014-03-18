package io.otonashi.cache;

import io.otonashi.BufferArrayOps;

public class BadBufferException extends StorageIOException {

    private BadBufferException(String message) {
        super(message);
    }

    public static void checkBufferParameters(byte[] buf, int offset, int length) throws BadBufferException {
        String message = BufferArrayOps.checkBufferParameters(buf, offset, length);
        if (message != null) {
            throw new BadBufferException(message);
        }
    }

}
