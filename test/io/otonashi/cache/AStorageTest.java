package io.otonashi.cache;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public abstract class AStorageTest {

    protected static final String KEY = "key";

    protected ContentStorage storage;

    @BeforeClass
    public void setUpClass() throws Exception {
    }

    @AfterClass
    public void tearDownClass() throws Exception {
    }

    private void write(String key, String value) throws StorageIOException {
        try (ContentSink sink = storage.getSink(key)) {
            sink.write(value.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void writeAndRead() throws IOException {
        write(KEY, KEY);

        byte[] buf = new byte[1024];
        int length;
        try (ContentSource source = storage.getSource(KEY)) {
            length = source.read(buf);
        }
        assertEquals(new String(buf, 0, length, StandardCharsets.UTF_8), KEY);
    }

    @Test
    public void writeExpireRead() throws IOException {
        write(KEY, KEY);
        storage.expire(KEY);
        try (ContentSource source = storage.getSource(KEY)) {
            assertNull(source);
        }
    }

    @Test(expectedExceptions = OverriddenSinkException.class)
    public void override() throws IOException {
        try (ContentSink sink1 = storage.getSink(KEY)) {
            sink1.write("1".getBytes(StandardCharsets.UTF_8));
            try (ContentSink sink2 = storage.getSink(KEY)) {
                sink2.write("2".getBytes());
                sink1.write("1".getBytes());
            }
        }
    }

}
