package edu.common.filter;

import edu.common.type.DataType;
import edu.common.validator.DataTypeValidator;

import java.util.*;

public class DataTypeFilter {
    private final Map<DataType, List<String>> dataByType;
    private final DataTypeValidator validator;

    public DataTypeFilter(DataTypeValidator validator) {
        this.validator = validator;

        dataByType = new EnumMap<>(DataType.class);
    }

    private void addDataByType(DataType type, String value) {
        if (!dataByType.containsKey(type)) {
            dataByType.put(type, new ArrayList<>());
        }

        dataByType.get(type).add(value);
    }

    public void filter(String line) {
        String trimmed = line.trim();
        DataType type = validator.detectType(trimmed);
        addDataByType(type, line);
    }

    public Map<DataType, List<String>> getFilteredData() {
        return Collections.unmodifiableMap(dataByType);
    }
}
