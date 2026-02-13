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

    private final View view;
    private final String filePrefix;
    private final String outputDirectory;
    private final boolean appendMode;

    public ResultFileLoader(View view, String filePrefix, String outputDirectory, boolean appendMode) {
        this.view = view;
        this.filePrefix = filePrefix;
        this.outputDirectory = outputDirectory;
        this.appendMode = appendMode;
    }

    @Override
    public void load(Map<DataType, List<String>> data) {
        if (data.isEmpty()) {
            return;
        }

        try {
            createOutputDirectory();

            for (var entry : data.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    writeToFile(entry.getKey(), entry.getValue());
                }
            }

            view.printMessage("Данные успешно записаны!");

        } catch (IOException e) {
            throw new ProcessingException("Ошибка записи результатов", e);
        }
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
    }
}
