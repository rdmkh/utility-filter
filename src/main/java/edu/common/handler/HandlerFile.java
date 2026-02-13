package edu.common.handler;

import edu.common.exception.ProcessingException;
import edu.common.filter.DataTypeFilter;
import edu.common.statistics.Statistics;
import edu.common.statistics.StatisticsFactory;
import edu.common.statistics.StatisticsType;
import edu.common.type.DataType;
import edu.common.validator.DataTypeValidator;
import edu.common.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HandlerFile implements Handler {

    private static final String FILE_NOT_EXIST_MESSAGE = "Файл '%s' не существует.";

    private final List<Path> filePaths;
    private final View view;
    private final DataTypeFilter dataFilter;

    public HandlerFile(List<Path> filePaths, View view, DataTypeValidator validator) {
        this.filePaths = filePaths;
        this.view = view;
        this.dataFilter = new DataTypeFilter(validator);
    }

    @Override
    public Map<DataType, List<String>> processFiles(StatisticsType statsType) {

        removeNonExistentFiles();

        if (filePaths.isEmpty()) {
            view.printMessage("Повторите ввод существующих файлов!");
            return Collections.emptyMap();
        }

        try {
            readAndFilterFiles();
            Map<DataType, List<String>> filteredData = dataFilter.getFilteredData();

            if (statsType != null) {
                Statistics statistics = StatisticsFactory.create(statsType);
                String stats = statistics.getStatistics(filteredData);
                view.printMessage(stats);
            }

            return filteredData;

        } catch (IOException e) {
            throw new ProcessingException("Ошибка обработки файлов", e);
        }
    }

    private void readAndFilterFiles() throws IOException {
        for (Path filePath : filePaths) {
            readFile(filePath);
        }
    }

    private void readFile(Path filePath) throws IOException {
        try (BufferedReader bf = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = bf.readLine()) != null) {
                if (!line.isBlank()) {
                    dataFilter.filter(line);
                }
            }
        }
    }

    private void removeNonExistentFiles() {
        filePaths.removeIf(
                path -> {
                    if (!Files.exists(path)) {
                        view.printfMessage(FILE_NOT_EXIST_MESSAGE, path);
                        return true;
                    }
                    return false;
                });
    }
}
