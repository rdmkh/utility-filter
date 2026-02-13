package edu.common.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatsStatistics {
    private int count;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal sum;

    public FloatsStatistics() {
        reset();
    }

    public void reset() {
        count = 0;
        min = null;
        max = null;
        sum = null;
    }

    public void add(String value) {
        try {
            BigDecimal num = new BigDecimal(value);
            count++;
            if (count == 1) {
                min = max = sum = num;
            } else {
                min = min.min(num);
                max = max.max(num);
                sum = sum.add(num);
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка парсинга вещественного числа: " + value);
        }
    }

    private String avg() {
        if (count == 0) {
            return "0";
        }

        return sum.divide(BigDecimal.valueOf(count), 10, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String getShortStatistics() {
        return String.format("Вещественные числа: { кол-во элементов - %d }", count);
    }

    public String getFullStatistics() {
        if (count == 0) {
            return "Вещественные числа: нет данных";
        }

        return String.format(
                "Вещественные числа: {\n кол-во: %d,\n минимум: %s,\n максимум: %s,\n сумма: %s,\n среднее: %s\n }",
                count, min.toPlainString(), max.toPlainString(),
                sum.toPlainString(), avg());
    }
}
