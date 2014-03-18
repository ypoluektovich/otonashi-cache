package io.otonashi.cache.memory;

import io.otonashi.cache.AStorageTest;
import org.testng.annotations.BeforeClass;

public class MemoryStorageTest extends AStorageTest {

    @Override
    @BeforeClass
    public void setUpClass() throws Exception {
        storage = new MemoryStorage();
    }

}
