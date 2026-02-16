package edu.common.statistics;

import edu.common.type.DataType;

import java.util.List;
import java.util.Map;

public abstract class AbstractStatistics implements Statistics {
    private final IntegersStatistics intStats;
    private final StringsStatistics stringStats;
    private final FloatsStatistics floatStats;

    public AbstractStatistics() {
        intStats = new IntegersStatistics();
        stringStats = new StringsStatistics();
        floatStats = new FloatsStatistics();
    }

    @Override
    public String getStatistics(Map<DataType, List<String>> filteredData) {
        for (var entry : filteredData.entrySet()) {
            List<String> values = entry.getValue();

            switch (entry.getKey()) {
                case INTEGER -> values.forEach(intStats::add);
                case STRING -> values.forEach(stringStats::add);
                case FLOAT -> values.forEach(value -> floatStats.add(stripFloatSuffix(value)));
            }
        }

        return formatStatistics(intStats, floatStats, stringStats);
    }

    private String stripFloatSuffix(String datum) {
        char lastChar = datum.charAt(datum.length() - 1);
        return switch (lastChar) {
            case 'f', 'F', 'd', 'D' -> datum.substring(0, datum.length() - 1);
            default -> datum;
        };
    }

    protected abstract String formatStatistics(IntegersStatistics intStats,
                                               FloatsStatistics floatStats,
                                               StringsStatistics stringStats);
}
