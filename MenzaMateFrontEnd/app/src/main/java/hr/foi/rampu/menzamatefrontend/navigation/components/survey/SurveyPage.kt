package hr.foi.rampu.menzamatefrontend.navigation.components.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import hr.foi.rampu.menzamatefrontend.viewmodels.SurveyViewModel
import models.Survey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyScreen(
    navController: NavHostController,
    surveyViewModel: SurveyViewModel,
    userId: Int
) {
    val availableSurveys by surveyViewModel.availableSurveys.collectAsState()
    val completedSurveys by surveyViewModel.completedSurveys.collectAsState()

    var selectedTab by remember { mutableStateOf("Dostupne") }
    var previewSurvey by remember { mutableStateOf<Survey?>(null) }
    var showSurveyPage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        surveyViewModel.fetchAllAnswersByUserId(userId)
        surveyViewModel.fetchAvailableSurveys(userId)
        surveyViewModel.fetchCompletedSurveys(userId)
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
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterButton(
                        text = "Dostupne ankete",
                        isSelected = selectedTab == "Dostupne",
                        onClick = { selectedTab = "Dostupne" }
                    )
                    FilterButton(
                        text = "Ispunjene ankete",
                        isSelected = selectedTab == "Ispunjene",
                        onClick = { selectedTab = "Ispunjene" }
                    )
                }

                when (selectedTab) {
                    "Dostupne" -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(availableSurveys) { survey ->
                                SurveyResultCard(survey = survey) {
                                    previewSurvey = survey
                                    showSurveyPage = true
                                }
                            }
                        }
                    }
                    "Ispunjene" -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(completedSurveys) { survey ->
                                SurveyResultCard(survey = survey) {
                                    previewSurvey = survey
                                    showSurveyPage = true
                                }
                            }
                        }
                    }
                }

                if (showSurveyPage && previewSurvey != null) {
                    Dialog(onDismissRequest = { showSurveyPage = false }) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(0.8f)
                                .shadow(8.dp, shape = MaterialTheme.shapes.medium),
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ViewSurveyPage(
                                survey = previewSurvey!!,
                                isCompleted = selectedTab == "Ispunjene",
                                userId = userId,
                                onSubmit = {
                                    surveyViewModel.fetchAvailableSurveys(userId)
                                    surveyViewModel.fetchCompletedSurveys(userId)
                                    showSurveyPage = false
                                },
                                onBack = { showSurveyPage = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFBA68C8) else Color(0xFFF3E5F5),
            contentColor = if (isSelected) Color.White else Color(0xFFBA68C8)
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
    }
}
