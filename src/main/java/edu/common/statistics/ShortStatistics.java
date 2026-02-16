package edu.common.statistics;


public class ShortStatistics extends AbstractStatistics {

    @Override
    protected String formatStatistics(IntegersStatistics intStats,
                                      FloatsStatistics floatStats,
                                      StringsStatistics stringStats) {

        return "------ Статистика ------\n" +
                intStats.getShortStatistics() + "\n" +
                floatStats.getShortStatistics() + "\n" +
                stringStats.getShortStatistics() + "\n" +
                "------------------------\n";
    }
}
