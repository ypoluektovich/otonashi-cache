package io.otonashi.cache.file;

import io.otonashi.cache.ContentSink;
import io.otonashi.cache.SinkCloseException;
import io.otonashi.cache.SinkOpenException;
import io.otonashi.cache.SinkWriteException;
import io.otonashi.cache.StorageCallback;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

final class FileSink extends ContentSink<FileHandle> {

    private OutputStream stream;

    FileSink(Object key, FileStorage storage, StorageCallback storageCallback) throws SinkOpenException {
        super(key, new FileHandle(prepareDirAndGetFile(storage), storageCallback), storage);
    }

    private static Path prepareDirAndGetFile(FileStorage storage) throws SinkOpenException {
        String hex = String.format("%016x", storage.sinkIndexSource.getAndIncrement());
        Path prefixDir = storage.storageRoot.resolve(hex.substring(0, 14));
        try {
            Files.createDirectories(prefixDir);
        } catch (IOException e) {
            throw new SinkOpenException(e, "failed to prepare content sink directory %s", prefixDir);
        }
        return prefixDir.resolve(hex.substring(14));
    }

    @Override
    protected void doOpen() throws SinkOpenException {
        try {
            stream = new BufferedOutputStream(Files.newOutputStream(handle.file));
        } catch (IOException e) {
            throw new SinkOpenException(e, "failed to open content sink %s", handle.file);
        }
    }

    @Override
    protected void doWrite(byte[] buf, int offset, int length) throws SinkWriteException {
        try {
            stream.write(buf, offset, length);
        } catch (IOException e) {
            throw new SinkWriteException(e);
        }
    }

    @Override
    protected void doClose() throws SinkCloseException {
        try {
            stream.close();
        } catch (IOException e) {
            throw new SinkCloseException(e);
        } finally {
            stream = null;
        }
    }

}
