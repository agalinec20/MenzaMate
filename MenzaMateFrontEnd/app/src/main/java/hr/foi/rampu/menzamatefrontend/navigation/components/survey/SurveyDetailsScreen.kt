package hr.foi.rampu.menzamatefrontend.navigation.components.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hr.foi.rampu.menzamatefrontend.statictics.StatisticsFactory
import hr.foi.rampu.menzamatefrontend.statictics.StatisticsType
import hr.foi.rampu.statisticscore.statictics.IStatistics
import hr.foi.rampu.menzamatefrontend.viewmodels.EmployeeSurveyViewModel
import models.Survey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyDetailsScreen(
    survey: Survey?,
    viewModel: EmployeeSurveyViewModel,
    selectedType: StatisticsType,
    onBack: () -> Unit
) {
    if (survey == null) {
        Text("Greška: Anketa nije pronađena", modifier = Modifier.padding(16.dp))
        return
    }

    val surveyAnswers by viewModel.surveyAnswers.collectAsState()
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var localSelectedType by remember { mutableStateOf(selectedType) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(survey.surveyId) {
        viewModel.fetchAnswersBySurveyId(survey.surveyId)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3E5F5),
                        Color(0xFFE1BEE7)
                    )
                )
            )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Detalji ankete",
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Povratak",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFBA68C8)
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (surveyAnswers.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nema dostupnih odgovora.")
                    }
                } else {
                    val groupedAnswers = surveyAnswers.groupBy { it.questionId }
                    val totalQuestions = groupedAnswers.size

                    if (totalQuestions == 0) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Nema dostupnih pitanja.")
                        }
                    } else {
                        val currentQuestionId = groupedAnswers.keys.elementAtOrNull(currentQuestionIndex)
                            ?: return@Column

                        val questionText = groupedAnswers[currentQuestionId]
                            ?.firstOrNull()
                            ?.questionText
                            ?: "Nepoznato pitanje"

                        val questionAnswers = groupedAnswers[currentQuestionId] ?: emptyList()

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = questionText,
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = FontFamily.SansSerif
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                listOf(StatisticsType.GRAPH, StatisticsType.TABLE).forEach { type ->
                                    val isSelected = (localSelectedType == type)
                                    Button(
                                        onClick = { localSelectedType = type },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) Color(0xFFBA68C8) else Color(0xFFEAEAEA),
                                            contentColor = if (isSelected) Color.White else Color.Black
                                        )
                                    ) {
                                        Text(
                                            text = if (type == StatisticsType.GRAPH)
                                                "Grafički prikaz"
                                            else "Tablični prikaz",
                                            fontFamily = FontFamily.SansSerif
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            val distribution = (1..5).associateWith { rating ->
                                questionAnswers.count { it.responses.toIntOrNull() == rating }
                            }

                            if (localSelectedType == StatisticsType.GRAPH) {
                                val statisticsModule: IStatistics =
                                    StatisticsFactory.getStatisticsModule(StatisticsType.GRAPH)
                                statisticsModule.DisplayStatistics(distribution)
                            } else {
                                TableStatistics(distribution)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
                                enabled = currentQuestionIndex > 0,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFBA68C8),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Prethodno pitanje", fontFamily = FontFamily.SansSerif)
                            }
                            Button(
                                onClick = {
                                    if (currentQuestionIndex < totalQuestions - 1) currentQuestionIndex++
                                },
                                enabled = currentQuestionIndex < totalQuestions - 1,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFBA68C8),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Sljedeće pitanje", fontFamily = FontFamily.SansSerif)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TableStatistics(distribution: Map<Int, Int>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ocjena",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Broj glasova",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            distribution.toSortedMap().forEach { (rating, freq) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = rating.toString(),
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = freq.toString(),
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }
    }
}
