package io.otonashi.cache;

public final class SourceInfo<H extends StoredDataHandle> {

    public final H handle;

    private int interest;

    private boolean markedForRelease;

    SourceInfo(H handle) {
        this.handle = handle;
    }

    synchronized void markForRelease() {
        markedForRelease = true;
        maybeRelease();
    }

    synchronized boolean incInterest() {
        if (markedForRelease) {
            return false;
        }
        ++interest;
        return true;
    }

    synchronized void decInterest() {
        --interest;
        maybeRelease();
    }

    private void maybeRelease() {
        if (interest == 0 && markedForRelease) {
            handle.release();
        }
    }

}
