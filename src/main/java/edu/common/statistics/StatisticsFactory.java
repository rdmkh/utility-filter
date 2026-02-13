package edu.common.statistics;

public final class StatisticsFactory {
    private StatisticsFactory() {}

    public static Statistics create(StatisticsType type) {
        return switch (type) {
            case FULL -> new FullStatistics();
            case SHORT -> new ShortStatistics();
            default -> throw new IllegalArgumentException("Неизвестный тип статистики: " + type);
        };
    }
}
