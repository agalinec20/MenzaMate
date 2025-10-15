package hr.foi.rampu.menzamatefrontend.statictics

import hr.foi.rampu.graphmodule.graphscreen.GraphStatistics
import hr.foi.rampu.statisticscore.statictics.IStatistics
import hr.foi.rampu.tablemodule.screen.TableStatistics

enum class StatisticsType { GRAPH , TABLE}

object StatisticsFactory {
    fun getStatisticsModule(type: StatisticsType): IStatistics {
        return when (type) {
            StatisticsType.GRAPH -> GraphStatistics()
            StatisticsType.TABLE -> TableStatistics()

        }
    }
}
