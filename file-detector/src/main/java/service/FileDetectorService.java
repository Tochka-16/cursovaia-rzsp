package service;

import model.FileSignature;
import util.SignatureLoader;
import util.SignatureMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileDetectorService {

    private static final Logger log = LoggerFactory.getLogger(FileDetectorService.class);
    private final List<FileSignature> signatures = SignatureLoader.load();

    public Optional<FileSignature> detect(Path file) {
        try {
            byte[] bytes = Files.readAllBytes(file);
            for (FileSignature sig : signatures) {
                if (SignatureMatcher.match(bytes, sig.getMagicBytes(), sig.getOffset())) {
                    return Optional.of(sig);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла {}", file, e);
        }
        return Optional.empty();
    }

    public Path restoreExtension(Path file, String ext) throws IOException {
        Path newFile = file.resolveSibling(file.getFileName() + "." + ext);
        return Files.move(file, newFile);
    }
}
