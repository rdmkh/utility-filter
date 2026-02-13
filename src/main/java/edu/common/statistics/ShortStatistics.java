package edu.common.statistics;

import edu.common.type.DataType;

import java.util.List;
import java.util.Map;

public class ShortStatistics implements Statistics {

    private final IntegersStatistics intStats;
    private final StringsStatistics stringStats;
    private final FloatsStatistics floatStats;

    public ShortStatistics() {
        intStats = new IntegersStatistics();
        stringStats = new StringsStatistics();
        floatStats = new FloatsStatistics();
    }

    @Override
    public String getStatistics(Map<DataType, List<String>> filteredData) {
        for (Map.Entry<DataType, List<String>> entry : filteredData.entrySet()) {

            DataType type = entry.getKey();
            List<String> values = entry.getValue();

            switch (type) {
                case INTEGER -> values.forEach(intStats::add);
                case STRING -> values.forEach(stringStats::add);
                case FLOAT -> values.forEach(floatStats::add);
            }
        }

        return generateShortStatistics();
    }

    private String generateShortStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append("------ Статистика ------\n");
        builder.append(intStats.getShortStatistics()).append("\n");
        builder.append(floatStats.getShortStatistics()).append("\n");
        builder.append(stringStats.getShortStatistics()).append("\n");
        builder.append("------------------------\n");
        return builder.toString();
    }
}
