package edu.common.type;

public enum DataType {

    INTEGER("integers.txt"),
    FLOAT("floats.txt"),
    STRING("strings.txt");

    private final String defaultFileName;

    DataType(String defaultFileName) {
        this.defaultFileName = defaultFileName;
    }

    public String getDefaultFileName() {
        return defaultFileName;
    }
}
