package edu.common.validator;

import edu.common.type.DataType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataTypeValidator {

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

        try {
            new BigDecimal(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isInteger(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }

        try {
            new BigInteger(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
