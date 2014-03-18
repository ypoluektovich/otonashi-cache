package io.otonashi.cache.file;

import io.otonashi.cache.StorageCallback;
import io.otonashi.cache.StoredDataHandle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class FileHandle extends StoredDataHandle {

    final Path file;

    FileHandle(Path file, StorageCallback callback) {
        super(callback);
        this.file = file;
    }

    @Override
    protected final void doRelease() throws IOException {
        Files.deleteIfExists(file);
    }

}
