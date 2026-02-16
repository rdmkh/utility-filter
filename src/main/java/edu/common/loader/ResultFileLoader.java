package edu.common.loader;

import edu.common.exception.ProcessingException;
import edu.common.type.DataType;
import edu.common.view.View;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public class ResultFileLoader implements Loader {

    private static final String IF_EMPTY_DATA_MESSAGE =
            "Нет данных для сохранения результатов. Заполните исходные файлы данными.";

    private final View view;
    private final String filePrefix;
    private final String outputDirectory;
    private final boolean appendMode;

    public ResultFileLoader(
            View view, String filePrefix, String outputDirectory, boolean appendMode) {
        this.view = view;
        this.filePrefix = filePrefix;
        this.outputDirectory = outputDirectory;
        this.appendMode = appendMode;
    }

    @Override
    public void load(Map<DataType, List<String>> data) {

        if (isEmptyData(data)) {
            view.printMessage(IF_EMPTY_DATA_MESSAGE);
            return;
        }

        try {
            createOutputDirectory();

            for (var entry : data.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    writeToFile(entry.getKey(), entry.getValue());
                }
            }

        } catch (IOException e) {
            throw new ProcessingException("Ошибка записи результатов", e);
        }
    }

    private boolean isEmptyData(Map<DataType, List<String>> data) {
        for (List<String> list : data.values()) {
            if (!list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void createOutputDirectory() throws IOException {
        if (!outputDirectory.isEmpty()) {
            Files.createDirectories(Path.of(outputDirectory));
        }
    }

    private void writeToFile(DataType dataType, List<String> data) throws IOException {
        String fileName = filePrefix + dataType.getDefaultFileName();
        Path filePath = outputDirectory.isEmpty() ? Path.of(fileName) : Path.of(outputDirectory, fileName);
        StandardOpenOption option = appendMode ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;

        Files.write(
                filePath,
                data,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                option);

        String message = "Имя файла:[%s]\nДиректория: [%s]\n";
        String dir = outputDirectory.isEmpty() ? "Текущая" : filePath.toString();

        view.printfMessage("Данные успешно записаны:\n" + message, fileName, dir);
    }
}
