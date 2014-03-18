package io.otonashi.cache;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ContentSink<H extends StoredDataHandle> implements AutoCloseable {

    private static enum State {
        NEW, OPEN, OVERRIDDEN, CLOSED
    }

    private final Object key;

    private final ContentStorage<H, ?> storage;

    protected final H handle;

    private final AtomicReference<State> state = new AtomicReference<>(State.NEW);

    protected ContentSink(Object key, H handle, ContentStorage<H, ?> storage) {
        this.key = key;
        this.storage = storage;
        this.handle = handle;
    }


    final synchronized void open() throws SinkOpenException {
        if (state.get() != State.NEW) {
            throw new SinkOpenException("cannot reopen content sink");
        }
        doOpen();
        state.set(State.OPEN);
    }

    protected abstract void doOpen() throws SinkOpenException;


    public final void write(byte[] buf) throws ClosedSinkException, BadBufferException, SinkWriteException {
        write(buf, 0, buf.length);
    }

    public final synchronized void write(byte[] buf, int offset, int length) throws ClosedSinkException, BadBufferException, SinkWriteException {
        State state = this.state.get();
        if (state != State.OPEN) {
            if (state == State.OVERRIDDEN) {
                try {
                    close();
                } catch (SinkCloseException e) {
                    throw new OverriddenSinkException(e);
                }
                throw new OverriddenSinkException();
            }
            throw new ClosedSinkException();
        }
        BadBufferException.checkBufferParameters(buf, offset, length);
        doWrite(buf, offset, length);
    }

    protected abstract void doWrite(byte[] buf, int offset, int length) throws SinkWriteException;


    final void override() {
        State state;
        do {
            state = this.state.get();
        } while (state.compareTo(State.OVERRIDDEN) < 0 && !this.state.compareAndSet(state, State.OVERRIDDEN));
    }


    @Override
    public final synchronized void close() throws SinkCloseException, OverriddenSinkException {
        State state = this.state.get();
        if (state == State.NEW || state == State.CLOSED) {
            return;
        }
        try {
            doClose();
            if (state == State.OVERRIDDEN) {
                handle.release();
            } else if (!storage.commit(key, handle, this)) {
                handle.release();
                throw new OverriddenSinkException();
            }
        } finally {
            this.state.set(State.CLOSED);
        }
    }

    protected abstract void doClose() throws SinkCloseException;

}
