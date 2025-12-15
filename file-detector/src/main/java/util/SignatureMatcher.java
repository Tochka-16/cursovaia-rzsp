package util;

public class SignatureMatcher {
    public static boolean match(byte[] fileBytes, byte[] magic, int offset) {
        if (fileBytes.length < offset + magic.length) return false;
        for (int i = 0; i < magic.length; i++) {
            if (fileBytes[offset + i] != magic[i]) return false;
        }
        return true;
    }
}
