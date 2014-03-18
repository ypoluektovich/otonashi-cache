package io.otonashi.cache.memory;

import io.otonashi.cache.StoredDataHandle;

final class NopHandle extends StoredDataHandle {

    static final NopHandle INSTANCE = new NopHandle();

    private NopHandle() {
        super(null);
    }

    @Override
    protected void doRelease() throws Throwable {
        // do nothing
    }

}
