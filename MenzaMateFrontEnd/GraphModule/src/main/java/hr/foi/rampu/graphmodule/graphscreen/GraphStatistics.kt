package hr.foi.rampu.graphmodule.graphscreen

import androidx.compose.runtime.Composable

import hr.foi.rampu.graphmodule.graphscreen.GraphChart
import hr.foi.rampu.statisticscore.statictics.IStatistics

class GraphStatistics : IStatistics {
    @Composable
    override fun DisplayStatistics(data: Map<Int, Int>) {
        GraphChart(distribution = data)
    }
}
