package service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileDetectorServiceTest {

    private final FileDetectorService service = new FileDetectorService();

    @Test
    void testRestoreExtension(@org.junit.jupiter.api.io.TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("file");
        Files.write(file, new byte[]{0x01, 0x02});
        Path renamed = service.restoreExtension(file, "bin");
        assertTrue(Files.exists(renamed));
        assertEquals("file.bin", renamed.getFileName().toString());
    }
}
