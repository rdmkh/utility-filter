package edu.common.validator;

import edu.common.type.DataType;

import java.util.regex.Pattern;

public class DataTypeValidator {

    private static final String REGEX_FLOATS = "^[+-]?\\d*(\\.\\d+)?([eE][+-]?\\d+)?[fF]?[dD]?$";
    private static final String REGEX_INTEGERS = "^-?\\d+$";

    private static final Pattern PATTERN_FLOATS = Pattern.compile(REGEX_FLOATS);
    private static final Pattern PATTERN_INTEGERS = Pattern.compile(REGEX_INTEGERS);

    public DataType detectType(String value) {
        if (validateValue(PATTERN_INTEGERS, value)) {
            return DataType.INTEGER;
        } else if (validateValue(PATTERN_FLOATS, value)) {
            return DataType.FLOAT;
        } else {
            return DataType.STRING;
        }
    }

    private boolean validateValue(Pattern pattern, String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }

        return pattern.matcher(data).matches();
    }
}
