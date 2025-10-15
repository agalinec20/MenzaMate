package hr.foi.rampu.tablemodule.screen

import androidx.compose.runtime.Composable
import hr.foi.rampu.statisticscore.statictics.IStatistics

class TableStatistics : IStatistics {
    @Composable
    override fun DisplayStatistics(data: Map<Int, Int>) {
        TableScreen(distribution = data)
    }
}