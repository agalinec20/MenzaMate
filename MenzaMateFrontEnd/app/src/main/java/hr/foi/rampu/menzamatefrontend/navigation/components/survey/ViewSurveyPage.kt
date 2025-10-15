package hr.foi.rampu.menzamatefrontend.navigation.components.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.SurveyViewModel
import kotlinx.coroutines.launch
import models.Survey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSurveyPage(
    survey: Survey,
    isCompleted: Boolean,
    userId: Int,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    surveyViewModel: SurveyViewModel = viewModel()
) {
    val answers = remember { mutableStateListOf<Int?>(null) }

    LaunchedEffect(userId) {
        surveyViewModel.fetchAllAnswersByUserId(userId)
    }

    val surveyAnswers by surveyViewModel.surveyAnswers.collectAsState(initial = emptyList())

    LaunchedEffect(surveyAnswers) {
        answers.clear()
        survey.questions?.forEach { question ->
            val answer = surveyAnswers.find { it.questionId == question.questionId }
                ?.responses?.toIntOrNull()
            answers.add(answer)
        }
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
                            text = survey.surveyName ?: "Anketa",
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                survey.surveyName?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    survey.questions?.forEachIndexed { index, question ->
                        item {
                            question.questionText?.let { questionText ->
                                Text(
                                    text = questionText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            if (isCompleted) {
                                val userResponse = surveyAnswers.find { it.questionId == question.questionId }
                                if (userResponse != null) {
                                    Text(
                                        text = userResponse.responses,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                } else {
                                    Text(
                                        text = "No answer",
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    (1..5).forEach { option ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            RadioButton(
                                                selected = answers.getOrNull(index) == option,
                                                onClick = {
                                                    if (answers.size > index) answers[index] = option
                                                    else answers.add(option)
                                                },
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Color(0xFFBA68C8)
                                                )
                                            )
                                            Text(
                                                text = option.toString(),
                                                fontFamily = FontFamily.SansSerif
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                if (!isCompleted) {
                    Button(
                        onClick = {
                            surveyViewModel.viewModelScope.launch {
                                val jobs = survey.questions?.mapIndexed { index, question ->
                                    val response = answers.getOrNull(index)?.toString() ?: ""
                                    launch {
                                        surveyViewModel.submitAnswerToSurvey(
                                            questionId = question.questionId,
                                            surveyId = survey.surveyId,
                                            userId = userId,
                                            response = response
                                        )
                                    }
                                } ?: emptyList()
                                jobs.forEach { it.join() }
                                surveyViewModel.fetchAvailableSurveys(userId)
                                surveyViewModel.fetchCompletedSurveys(userId)
                                onSubmit()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBA68C8)
                        )
                    ) {
                        Text(
                            text = "Pošalji",
                            fontFamily = FontFamily.SansSerif,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
