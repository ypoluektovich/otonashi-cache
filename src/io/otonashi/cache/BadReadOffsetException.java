package io.otonashi.cache;

public class BadReadOffsetException extends StorageIOException {

    private BadReadOffsetException(long offset, long dataSize) {
        super("offset " + offset + " is outside range [0, " + dataSize + "]");
    }

    public static void checkReadOffset(long offset, long dataSize) throws BadReadOffsetException {
        if (offset < 0 || offset > dataSize) {
            throw new BadReadOffsetException(offset, dataSize);
        }
    }

}
