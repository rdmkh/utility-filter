package edu.common.handler;

import edu.common.exception.ProcessingException;
import edu.common.filter.DataTypeFilter;
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

  private static final String FILE_NOT_EXIST_MESSAGE = "Файл '%s' не существует. Удалено.";
  private static final String EXISTING_FILES_MESSAGE = "Существующие файлы: ";

  private final List<Path> filePaths;
  private final View view;
  private final DataTypeFilter dataFilter;

  public HandlerFile(List<Path> filePaths, View view, DataTypeValidator validator) {
    this.filePaths = filePaths;
    this.view = view;
    this.dataFilter = new DataTypeFilter(validator);
  }

  @Override
  public Map<DataType, List<String>> processFileContents() {

    removeNonExistentFiles();

    if (filePaths.isEmpty()) {
      view.printMessage("Нет существующих файлов для обработки.");
      return Collections.emptyMap();
    }

    view.printMessage(EXISTING_FILES_MESSAGE + filePaths);

    try {

      Map<DataType, List<String>> filteredData = readAndFilterFiles();
      printFilteredData(filteredData);

      return filteredData;

    } catch (IOException e) {
      throw new ProcessingException("Ошибка обработки файлов", e);
    }
  }

  private Map<DataType, List<String>> readAndFilterFiles() throws IOException {
    for (Path filePath : filePaths) {
      readFile(filePath);
    }

    return dataFilter.getFilteredData();
  }

  private void readFile(Path filePath) throws IOException {
    try (BufferedReader bf = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
      String line;
      while ((line = bf.readLine()) != null) {
        if (!line.isBlank()) {
          dataFilter.processLines(line);
        }
      }
    }
  }

  private void removeNonExistentFiles() {
    filePaths.removeIf(
        path -> {
          if (!Files.exists(path)) {
            view.printMessage(FILE_NOT_EXIST_MESSAGE + path);
            return true;
          }
          return false;
        });
  }

  private void printFilteredData(Map<DataType, List<String>> data) {
    data.forEach((type, values) -> view.printMessage(type.name() + " : " + values));
  }
}
