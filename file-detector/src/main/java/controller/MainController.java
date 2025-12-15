package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.FileSignature;
import service.FileDetectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class MainController {

    @FXML private TextField filePathField;
    @FXML private Label resultLabel;
    @FXML private Button restoreButton;


    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final FileDetectorService detectorService = new FileDetectorService();

    private Path selectedFile;
    private FileSignature detectedSignature;

    @FXML
    private void onChooseFile() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            selectedFile = file.toPath();
            filePathField.setText(selectedFile.toString());
            resultLabel.setText("");
            restoreButton.setDisable(true);
            log.info("Выбран файл: {}", selectedFile);
        }
    }

    @FXML
    private void onDetect() {
        if (selectedFile == null) return;
        Optional<FileSignature> result = detectorService.detect(selectedFile);
        if (result.isPresent()) {
            detectedSignature = result.get();
            resultLabel.setText("Тип файла: " + detectedSignature.getFileType() +
                    "\nРасширения: " + String.join(", ", detectedSignature.getExtensions()));
            restoreButton.setDisable(false);
            log.info("Определён тип файла: {}", detectedSignature.getFileType());
        } else {
            resultLabel.setText("Тип файла не определён");
            log.warn("Тип файла не определён для {}", selectedFile);
        }
    }

    @FXML
    private void onRestore() {
        if (detectedSignature == null) return;
        try {
            String ext = detectedSignature.getExtensions().get(0);
            Path newFile = detectorService.restoreExtension(selectedFile, ext);
            resultLabel.setText("Файл переименован:\n" + newFile.getFileName());
            restoreButton.setDisable(true);
            log.info("Файл {} переименован в {}", selectedFile, newFile);
        } catch (IOException e) {
            resultLabel.setText("Ошибка при переименовании");
            log.error("Ошибка при восстановлении расширения", e);
        }
    }
}
