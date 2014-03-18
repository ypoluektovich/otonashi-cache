package io.otonashi;

public class BufferArrayOps {

    public static final String NULL_BUFFER = "null buffer";

    public static final String NEGATIVE_OFFSET = "negative buffer offset";

    public static final String NEGATIVE_LENGTH = "negative buffer length";

    public static final String RANGE_START_TOO_LARGE = "range starts out of buffer";

    public static final String RANGE_END_TOO_LARGE = "range ends out of buffer";

    /**
     * Check whether parameters of a byte array buffer are sane.
     *
     * @param buffer    the buffer with data to write.
     * @param offset    the offset in the buffer.
     * @param length    the size of the data.
     * @return One of: <ul>
     *     <li><code>null</code> if no problems are found;</li>
     *     <li>{@link #NULL_BUFFER} if <code>buffer == null</code>;</li>
     *     <li>{@link #NEGATIVE_OFFSET} if <code>offset < 0</code>;</li>
     *     <li>{@link #NEGATIVE_LENGTH} if <code>length < 0</code>;</li>
     *     <li>{@link #RANGE_START_TOO_LARGE} if <code>offset</code> is greater than the buffer length;</li>
     *     <li>{@link #RANGE_END_TOO_LARGE} if <code>(offset + length)</code> is greater than the buffer length.</li>
     * </ul>
     */
    public static String checkBufferParameters(byte[] buffer, int offset, int length) {
        if (buffer == null) {
            return NULL_BUFFER;
        }
        if (offset < 0) {
            return NEGATIVE_OFFSET;
        }
        if (length < 0) {
            return NEGATIVE_LENGTH;
        }
        int bufLength = buffer.length;
        if (offset > bufLength) {
            return RANGE_START_TOO_LARGE;
        }
        int rangeEnd = offset + length;
        if (rangeEnd < 0 || rangeEnd > bufLength) {
            return RANGE_END_TOO_LARGE;
        }
        return null;
    }

}
