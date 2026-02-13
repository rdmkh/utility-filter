package edu.common.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.util.regex.Pattern;

public class PathValidator implements IParameterValidator {

    private static final String FILE_REGEX = "^.+\\.txt$";

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (value == null || value.isBlank()) {
            throw new ParameterException("Параметр " + value + " не может быть пустым.");
        }

        if (!isValidPath(value)) {
            throw new ParameterException("Некорректный путь - " + value);
        }
    }

    private boolean isValidPath(String value) {
        return Pattern.compile(FILE_REGEX).matcher(value).matches();
    }
}
