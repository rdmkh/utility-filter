package edu.common.loader;

import edu.common.type.DataType;

import java.util.List;
import java.util.Map;

public interface Loader {
    void load(Map<DataType, List<String>> data);
}
