package hr.foi.rampu.feedbackresults.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Survey
import viewmodels.SurveyViewModel

@Composable
fun SurveyResultScreen(
    userId: Int,
    viewModel: SurveyViewModel,
    onSurveySelected: (Survey) -> Unit
) {
    var selectedSurvey by remember { mutableStateOf<Survey?>(null) }
    var selectedTab by remember { mutableStateOf("Sve ankete") }

    if (selectedSurvey == null) {
        LaunchedEffect(Unit) {
            viewModel.fetchAllSurveys()
            viewModel.fetchSurveysByUserId(userId)
        }

        val allSurveys by viewModel.allSurveys.collectAsState()
        val availableSurveys by viewModel.availableSurveys.collectAsState()

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilteredButton(
                    text = "Sve ankete",
                    isSelected = selectedTab == "Sve ankete",
                    onClick = { selectedTab = "Sve ankete" }
                )
                FilteredButton(
                    text = "Moje ankete",
                    isSelected = selectedTab == "Moje ankete",
                    onClick = { selectedTab = "Moje ankete" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                "Sve ankete" -> {
                    Text("Sve ankete", style = MaterialTheme.typography.titleLarge)
                    LazyColumn {
                        items(allSurveys) { survey ->
                            SurveyCard(survey = survey, onClick = { selectedSurvey = survey })
                        }
                    }
                }

                "Moje ankete" -> {
                    Text("Moje ankete", style = MaterialTheme.typography.titleLarge)
                    LazyColumn {
                        items(availableSurveys) { survey ->
                            SurveyCard(survey = survey, onClick = { selectedSurvey = survey })
                        }
                    }
                }
            }
        }
    } else {
        selectedSurvey?.let { survey ->
            SurveyDetailScreen(
                survey = survey,
                viewModel = viewModel,
                onBack = { selectedSurvey = null }
            )
        }
    }
}



@Composable
fun SurveyCard(survey: Survey, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.outlinedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = survey.surveyName ?: "Nepoznato ime ankete",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = survey.surveyDescription ?: "Nema opisa ankete",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(onClick = onClick) {
                Text("Pregled ankete")
            }
        }
    }
}

@Composable
fun FilteredButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}
