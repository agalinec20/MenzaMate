package hr.foi.rampu.menzamatefrontend.navigation.components.survey

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Survey
import hr.foi.rampu.menzamatefrontend.statictics.StatisticsType
import hr.foi.rampu.menzamatefrontend.viewmodels.EmployeeSurveyViewModel

@Composable
fun SurveyResultScreen(
    userId: Int,
    viewModel: EmployeeSurveyViewModel
) {
    var selectedSurvey by remember { mutableStateOf<Survey?>(null) }
    var selectedTab by remember { mutableStateOf("SVE ANKETE") }
    var selectedType by remember { mutableStateOf(StatisticsType.GRAPH) }

    LaunchedEffect(selectedTab) {
        if (selectedTab == "SVE ANKETE") {
            viewModel.fetchAllSurveys()
        } else {
            viewModel.fetchMySurveys()
        }
    }

    val surveys by if (selectedTab == "SVE ANKETE") {
        viewModel.allSurveys.collectAsState()
    } else {
        viewModel.mySurveys.collectAsState()
    }

    if (selectedSurvey == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF3E5F5),
                            Color(0xFFE1BEE7)
                        )
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilteredButton("Sve ankete", selectedTab == "SVE ANKETE") { selectedTab = "SVE ANKETE" }
                FilteredButton("Moje ankete", selectedTab == "MOJE ANKETE") { selectedTab = "MOJE ANKETE" }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(surveys) { survey ->
                    SurveyResultCard(survey) { selectedSurvey = survey }
                }
            }
        }
    } else {
        selectedSurvey?.let { survey ->
            SurveyDetailsScreen(
                survey = survey,
                viewModel = viewModel,
                selectedType = selectedType,
                onBack = { selectedSurvey = null }
            )
        }
    }
}

@Composable
fun SurveyResultCard(survey: Survey, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.outlinedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = survey.surveyName ?: "Nepoznato ime ankete",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = survey.surveyDescription ?: "Nema opisa ankete",
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.SansSerif)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = onClick) {
                Text("Pregled ankete", fontFamily = FontFamily.SansSerif)
            }
        }
    }
}

@Composable
fun FilteredButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
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
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
    }
}
