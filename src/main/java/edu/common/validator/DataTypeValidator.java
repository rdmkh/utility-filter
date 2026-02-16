package edu.common.validator;

import edu.common.type.DataType;

import java.util.regex.Pattern;

public class DataTypeValidator {

    private static final String REGEX_FLOATS = "^[+-]?\\d*(\\.\\d+)?([eE][+-]?\\d+)?[fF]?[dD]?$";
    private static final String REGEX_INTEGERS = "^-?\\d+$";

    private static final Pattern PATTERN_FLOATS = Pattern.compile(REGEX_FLOATS);
    private static final Pattern PATTERN_INTEGERS = Pattern.compile(REGEX_INTEGERS);

    public DataType detectType(String value) {
        if (isInteger(value)) {
            return DataType.INTEGER;
        } else if (isFloat(value)) {
            return DataType.FLOAT;
        } else {
            return DataType.STRING;
        }
    }

    public boolean isFloat(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }

        return PATTERN_FLOATS.matcher(data).matches();
    }

    public boolean isInteger(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }

        return PATTERN_INTEGERS.matcher(data).matches();
    }
}
