package edu.common.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class IntegersStatistics {

    private int count;
    private BigInteger max;
    private BigInteger min;
    private BigInteger sum;

    public IntegersStatistics() {
        reset();
    }

    public void add(String value) {
        try {
            BigInteger num = new BigInteger(value);
            count++;
            if (count == 1) {
                min = max = sum = num;
            } else {
                min = min.min(num);
                max = max.max(num);
                sum = sum.add(num);
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка парсинга целого числа: " + value);
        }
    }

    public String getShortStatistics() {
        return String.format("Целые числа: { кол-во элементов - %d }", count);
    }

    public void reset() {
        count = 0;
        min = null;
        max = null;
        sum = null;
    }

    public String getFullStatistics() {
        if (count == 0) return "Целые числа: нет данных";

        return String.format(
                "Целые числа: {\n кол-во: %d,\n минимум: %d,\n максимум: %d,\n сумма: %d,\n среднее: %s\n }",
                count, min, max, sum, avg());
    }

    private String avg() {
        if (count == 0) {
            return "0";
        }

        BigDecimal avg = new BigDecimal(sum).divide(new BigDecimal(count), 10, RoundingMode.HALF_UP);
        return avg.stripTrailingZeros().toPlainString();
    }
}
