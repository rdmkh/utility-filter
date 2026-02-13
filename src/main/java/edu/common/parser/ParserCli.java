package edu.common.parser;

import com.beust.jcommander.*;
import edu.common.statistics.StatisticsType;
import edu.common.validator.PathValidator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParserCli implements Parser {

    @Parameter(
            validateWith = PathValidator.class,
            description = "Исходные файлы (один или несколько .txt)",
            variableArity = true,
            required = true
    )
    private List<Path> filePaths = new ArrayList<>();

    @Parameter(
            names = {"-a"},
            description = "Режим добавления в существующие файлы (по умолчанию перезапись)",
            order = 1
    )
    private boolean recordMode = false;

    @Parameter(
            names = {"-p"},
            description = "Префикс для имён результирующих файлов (по умолчанию пусто)",
            order = 2
    )
    private String filePrefix = "";

    @Parameter(
            names = {"-o"},
            description = "Директория для сохранения результатов (по умолчанию текущая)",
            order = 3
    )
    private String outputDirectory = "";

    @Parameter(names = {"-s"}, description = "Краткая статистика (только количество)", order = 5)
    private boolean isShort = false;

    @Parameter(names = {"-f"}, description = "Полная статистика (min, max, сумма, среднее, длины строк)", order = 4)
    private boolean isFull = false;

    @Override
    public List<Path> getFilePaths() {
        return filePaths;
    }

    @Override
    public boolean getRecordModeToFile() {
        return recordMode;
    }

    @Override
    public String getFilePrefix() {
        return filePrefix;
    }

    @Override
    public String getOutputDirectory() {
        return outputDirectory;
    }

    @Override
    public Optional<StatisticsType> getStatisticsType() {
        if (isShort && isFull) {
            throw new ParameterException("Параметры -s и -f не могут использоваться одновременно");
        }

        return isShort
                ? Optional.of(StatisticsType.SHORT)
                : isFull ? Optional.of(StatisticsType.FULL) : Optional.empty();
    }
}
