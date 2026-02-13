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
        initDataByType();
    }

    private void initDataByType() {
        for (DataType type : DataType.values()) {
            dataByType.put(type, new ArrayList<>());
        }
    }

    public void filter(String line) {
        line = line.trim();
        DataType type = validator.detectType(line);
        dataByType.get(type).add(line);
    }

    public Map<DataType, List<String>> getFilteredData() {
        return Collections.unmodifiableMap(dataByType);
    }
}
