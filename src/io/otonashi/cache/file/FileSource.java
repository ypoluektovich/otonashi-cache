package io.otonashi.cache.file;

import io.otonashi.cache.ContentSource;
import io.otonashi.cache.SourceCloseException;
import io.otonashi.cache.SourceInfo;
import io.otonashi.cache.SourceOpenException;
import io.otonashi.cache.SourceReadException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

final class FileSource extends ContentSource<FileHandle> {

    private InputStream stream;

    FileSource(SourceInfo<FileHandle> sourceInfo) throws SourceOpenException {
        super(sourceInfo);
        try {
            stream = new BufferedInputStream(Files.newInputStream(sourceInfo.handle.file));
        } catch (IOException e) {
            throw new SourceOpenException(e, "failed to open %s", sourceInfo.handle.file);
        }
    }

    @Override
    protected boolean isClosed() {
        return stream == null;
    }

    @Override
    protected int doRead(byte[] buf, int offset, int length) throws SourceReadException {
        try {
            return stream.read(buf, offset, length);
        } catch (IOException e) {
            throw new SourceReadException(e);
        }
    }

    @Override
    protected void doClose() throws SourceCloseException {
        try {
            stream.close();
        } catch (IOException e) {
            throw new SourceCloseException(e);
        } finally {
            stream = null;
        }
    }

}
