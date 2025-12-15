package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FileSignature;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class SignatureLoader {
    public static List<FileSignature> load() {
        try {
            InputStream is = SignatureLoader.class.getResourceAsStream("/signatures.json");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(is, new TypeReference<List<FileSignature>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
