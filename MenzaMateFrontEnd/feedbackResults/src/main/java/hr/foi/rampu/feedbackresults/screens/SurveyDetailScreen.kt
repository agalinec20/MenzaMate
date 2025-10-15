package hr.foi.rampu.feedbackresults.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Answer
import models.Survey
import hr.foi.rampu.feedbackresults.screens.surveycomponents.EnhancedBarChart
import hr.foi.rampu.feedbackresults.screens.surveycomponents.StyledAnswersTable
import viewmodels.SurveyViewModel
@Composable
fun SurveyDetailScreen(
    survey: Survey,
    viewModel: SurveyViewModel,
    onBack: () -> Unit
) {
    val answers by viewModel.answers.collectAsState(initial = emptyList())
    val loading by viewModel.loading.collectAsState(initial = false)

    var currentQuestionIndex by remember { mutableStateOf(0) }

    LaunchedEffect(survey.surveyId) {
        viewModel.fetchAnswersBySurveyId(survey.surveyId)
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        when {
            loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            answers.isEmpty() -> {
                Text(
                    text = "Nema dostupnih odgovora.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                val groupedAnswers = answers
                    .filter { it.responses.isNotEmpty() }
                    .groupBy { it.questionId }
                    .toList()

                val totalQuestions = groupedAnswers.size

                if (groupedAnswers.isNotEmpty()) {
                    val (questionId, answersForQuestion) = groupedAnswers[currentQuestionIndex]

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(onClick = onBack) {
                                Text("Nazad")
                            }
                            Text(
                                text = "Detalji ankete",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.width(48.dp)) // Pražnina za poravnanje
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = answersForQuestion.firstOrNull()?.questionText ?: "Nepoznato pitanje",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SurveyQuestionSection(answers = answersForQuestion)

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    if (currentQuestionIndex > 0) currentQuestionIndex--
                                },
                                enabled = currentQuestionIndex > 0
                            ) {
                                Text("← Prethodno pitanje")
                            }
                            Button(
                                onClick = {
                                    if (currentQuestionIndex < totalQuestions - 1) currentQuestionIndex++
                                },
                                enabled = currentQuestionIndex < totalQuestions - 1
                            ) {
                                Text("Sljedeće pitanje →")
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun SurveyQuestionSection(answers: List<Answer>) {
    var showTable by remember { mutableStateOf(true) }

    val questionText = answers.firstOrNull()?.questionText ?: "Nepoznato pitanje"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { showTable = !showTable }) {
                Text(if (showTable) "Prikaz grafa" else "Prikaz tablice")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (showTable) {
            StyledAnswersTable(questionText = questionText, answers = answers)
        } else {
            val distribution = answers
                .mapNotNull { it.responses.toIntOrNull() }
                .groupingBy { it }
                .eachCount()

            if (distribution.isNotEmpty()) {
                EnhancedBarChart(distribution = distribution)
            } else {
                Text("Nema podataka za graf.")
            }
        }
    }
}
