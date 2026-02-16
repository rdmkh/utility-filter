package edu.common.statistics;

public class FullStatistics extends AbstractStatistics {
    @Override
    protected String formatStatistics(IntegersStatistics intStats,
                                      FloatsStatistics floatStats,
                                      StringsStatistics stringStats) {

        return "------ Статистика ------\n" +
                intStats.getFullStatistics() + "\n" +
                floatStats.getFullStatistics() + "\n" +
                stringStats.getFullStatistics() + "\n" +
                "------------------------\n";
    }
}
