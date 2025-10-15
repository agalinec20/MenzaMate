package hr.foi.rampu.feedbackresults.screens.surveycomponents

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnhancedBarChart(distribution: Map<Int, Int>) {
    val maxCount = distribution.values.maxOrNull() ?: 1

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Distribucija ocjena",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
            ) {
                val barWidth = size.width / (distribution.size * 3)
                val spacing = barWidth
                var xOffset = spacing

                distribution.forEach { (rating, count) ->
                    val barHeight = (count.toFloat() / maxCount) * size.height * 0.8f
                    drawRoundRect(
                        color = Color.Blue,
                        topLeft = Offset(xOffset, size.height - barHeight),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )

                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            rating.toString(),
                            xOffset + barWidth / 2,
                            size.height + 20.dp.toPx(),
                            Paint().apply {
                                color = android.graphics.Color.BLACK
                                textAlign = Paint.Align.CENTER
                                textSize = 32f
                            }
                        )
                    }
                    xOffset += barWidth + spacing
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "X-os: Ocjene | Y-os: Broj korisnika",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
