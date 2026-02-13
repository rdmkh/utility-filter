package edu.common.statistics;

import edu.common.type.DataType;

import java.util.List;
import java.util.Map;

public class FullStatistics implements Statistics {

    private final IntegersStatistics intStats;
    private final StringsStatistics stringStats;
    private final FloatsStatistics floatStats;

    public FullStatistics() {
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

        return generateFullStatistics();
    }

    private String generateFullStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append("------ Статистика ------\n");
        builder.append(intStats.getFullStatistics()).append("\n");
        builder.append(floatStats.getFullStatistics()).append("\n");
        builder.append(stringStats.getFullStatistics()).append("\n");
        builder.append("------------------------\n");
        return builder.toString();
    }
}
