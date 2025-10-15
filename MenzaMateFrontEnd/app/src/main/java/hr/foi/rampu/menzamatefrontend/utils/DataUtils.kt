package hr.foi.rampu.menzamatefrontend.utils

import android.util.Log
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


    @Composable
    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        Log.d("SurveyScreen", "Rendering surveys for date: $date")


        return try {
            val parsedDate: Date = inputFormat.parse(date) ?: Date()
            outputFormat.format(parsedDate)
        } catch (e: Exception) {
            ""
        }
    }
