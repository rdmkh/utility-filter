package edu.common.statistics;

public class StringsStatistics {
    private int count;
    private int minLength;
    private int maxLength;

    public StringsStatistics() {
        reset();
    }

    public void reset() {
        count = 0;
        minLength = Integer.MAX_VALUE;
        maxLength = Integer.MIN_VALUE;
    }

    public void add(String value) {
        int len = value.length();
        count++;
        if (count == 1) {
            minLength = len;
            maxLength = len;
        } else {
            minLength = Math.min(minLength, len);
            maxLength = Math.max(maxLength, len);
        }
    }

    public String getShortStatistics() {
        return String.format("Строки: { кол-во элементов - %d}", count);
    }

    public String getFullStatistics() {
        if (count == 0) {
            return "Строки: нет данных";
        }

        return String.format(
                "Строки: {\n кол-во: %d,\n мин. длина: %d,\n макс. длина: %d \n}",
                count, minLength, maxLength);
    }
}
