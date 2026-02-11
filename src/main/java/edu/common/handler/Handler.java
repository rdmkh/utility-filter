package edu.common.handler;

import edu.common.type.DataType;

import java.util.List;
import java.util.Map;

public interface Handler {
    Map<DataType, List<String>> processFileContents();
}
