package hr.foi.rampu.statisticscore.statictics

import androidx.compose.runtime.Composable

interface IStatistics {
    @Composable
    fun DisplayStatistics(data: Map<Int, Int>)
}