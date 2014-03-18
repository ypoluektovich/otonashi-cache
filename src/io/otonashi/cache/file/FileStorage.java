package io.otonashi.cache.file;

import io.otonashi.cache.ContentStorage;
import io.otonashi.cache.SinkOpenException;
import io.otonashi.cache.SourceInfo;
import io.otonashi.cache.SourceOpenException;
import io.otonashi.cache.StorageCallback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

public final class FileStorage extends ContentStorage<FileHandle, FileHandle> {

    final Path storageRoot;

    final AtomicLong sinkIndexSource = new AtomicLong();


    public FileStorage(Path storageRoot, StorageCallback callback) throws BadStorageRootException {
        super(callback);
        this.storageRoot = checkStorageRoot(storageRoot);
    }

    private static Path checkStorageRoot(Path storageRoot) throws BadStorageRootException {
        if (storageRoot == null) {
            throw new BadStorageRootException(BadStorageRootException.NULL);
        }
        try {
            Files.createDirectories(storageRoot);
        } catch (IOException e) {
            throw new BadStorageRootException(e, BadStorageRootException.NOT_EXISTS_AND_DIRECTORY);
        }
        if (!Files.isWritable(storageRoot)) {
            throw new BadStorageRootException(BadStorageRootException.NOT_WRITABLE);
        }
        return storageRoot;
    }


    @Override
    protected final FileSink createSink(Object key) throws SinkOpenException {
        return new FileSink(key, this, callback);
    }

    @Override
    protected final FileHandle getOutputHandle(FileHandle handle) {
        return handle;
    }

    @Override
    protected final FileSource createSource(SourceInfo<FileHandle> sourceInfo) throws SourceOpenException {
        return new FileSource(sourceInfo);
    }

}
