package io.otonashi.cache.memory;

import io.otonashi.cache.StoredDataHandle;

class ByteArrayHandle extends StoredDataHandle {

    byte[] data;

    ByteArrayHandle() {
        super(null);
    }

    @Override
    protected void doRelease() throws Throwable {
        data = null;
    }

}
