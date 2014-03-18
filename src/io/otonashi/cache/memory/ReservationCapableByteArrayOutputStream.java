package io.otonashi.cache.memory;

import java.io.ByteArrayOutputStream;

class ReservationCapableByteArrayOutputStream extends ByteArrayOutputStream {

    public synchronized int getCapacity() {
        return buf.length;
    }

    public synchronized int forceGrowth() {
        int oldCount = count;
        count = buf.length;
        try {
            write(0);
        } catch (OutOfMemoryError ignore) {
            // suppress the error
        }
        count = oldCount;
        return buf.length;
    }

}
