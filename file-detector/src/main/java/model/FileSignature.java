package model;

import java.util.List;

public class FileSignature {
    private String fileType;
    private List<String> extensions;
    private String magic;
    private int offset;

    public String getFileType() { return fileType; }
    public List<String> getExtensions() { return extensions; }
    public String getMagic() { return magic; }

    public int getOffset() { return offset; }

    public byte[] getMagicBytes() {
        // 1. Очищаем строку magic от лишних символов (например, запятых)
        String cleanedMagic = magic.replaceAll("[^0-9A-Fa-f\\s]", "");

        // 2. Разбиваем строку на отдельные шестнадцатеричные значения
        String[] bytes = cleanedMagic.trim().split("\\s+"); // Используем \\s+ для разделения по пробелам (включая табуляцию и переносы строк)

        byte[] result = new byte[bytes.length];

        // 3. Проходим по каждому элементу и преобразуем в байт
        for (int i = 0; i < bytes.length; i++) {
            String hexValue = bytes[i].trim(); // Убираем лишние пробелы

            // Проверяем, что строка не пустая и содержит только допустимые символы
            if (hexValue.isEmpty() || !hexValue.matches("[0-9A-Fa-f]+")) {
                throw new IllegalArgumentException("Некорректное шестнадцатеричное значение: " + hexValue);
            }

            try {
                result[i] = (byte) Integer.parseInt(hexValue, 16);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ошибка при парсинге шестнадцатеричного значения '" + hexValue + "': " + e.getMessage());
            }
        }
        return result;
    }
}
