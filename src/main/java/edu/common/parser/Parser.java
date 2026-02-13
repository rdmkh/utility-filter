package edu.common.parser;

import edu.common.statistics.StatisticsType;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface Parser {
    List<Path> getFilePaths();

    boolean getRecordModeToFile();

    String getFilePrefix();

    String getOutputDirectory();

    Optional<StatisticsType> getStatisticsType();
}
