package edu.common.statistics;

import edu.common.type.DataType;

import java.util.List;
import java.util.Map;

public interface Statistics {
    String getStatistics(Map<DataType, List<String>> filteredData);
}
