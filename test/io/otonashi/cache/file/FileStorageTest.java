package io.otonashi.cache.file;

import io.otonashi.cache.AStorageTest;
import io.otonashi.cache.StorageCallback;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileStorageTest extends AStorageTest {

    private Path storageRoot;

    @Override
    @BeforeClass
    public void setUpClass() throws Exception {
        storageRoot = Files.createTempDirectory("otonashi-cache-fs-test");
        storage = new FileStorage(
                storageRoot,
                new StorageCallback() {
                    @Override
                    public void releaseFailed(Throwable throwable) {
                        Assert.fail("Release failed", throwable);
                    }
                }
        );
    }

    @Override
    @AfterClass
    public void tearDownClass() throws Exception {
        Files.walkFileTree(
                storageRoot,
                new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        System.err.println("Visit failed: " + file);
                        exc.printStackTrace();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                }
        );
    }

}
