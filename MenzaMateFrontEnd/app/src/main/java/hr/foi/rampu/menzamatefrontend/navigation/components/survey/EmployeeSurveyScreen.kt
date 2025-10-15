package hr.foi.rampu.menzamatefrontend.navigation.components.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import models.Survey
import hr.foi.rampu.menzamatefrontend.viewmodels.EmployeeSurveyViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.EmployeeSurveyViewModelFactory
import hr.foi.rampu.menzamatefrontend.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeSurveyScreen(
    navController: NavHostController,
    userId: Int
) {
    val viewModel: EmployeeSurveyViewModel = viewModel(factory = EmployeeSurveyViewModelFactory(userId))
    val surveys by viewModel.allSurveys.collectAsState()
    var selectedSurvey by remember { mutableStateOf<Survey?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchAllSurveys()
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
            containerColor = Color.Transparent,
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(surveys) { survey ->
                            SurveyResultCard(survey) {
                                selectedSurvey = survey
                            }
                        }
                    }
                    Button(
                        onClick = { navController.navigate("create_survey") },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8))
                    ) {
                        Text(
                            text = "Kreiraj novu anketu",
                            fontFamily = FontFamily.SansSerif,
                            color = Color.White
                        )
                    }
                }
            }
        )

        if (selectedSurvey != null) {
            EmployeeSurveyDialog(
                survey = selectedSurvey!!,
                onDismiss = { selectedSurvey = null }
            )
        }
    }
}

@Composable
fun EmployeeSurveyDialog(
    survey: Survey,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Pregled ankete",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color(0xFFBA68C8)
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Zatvori",
                                tint = Color(0xFFBA68C8)
                            )
                        }
                    }

                    Text(
                        text = survey.surveyName ?: "Nepoznato ime ankete",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF333333)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        survey.questions?.forEachIndexed { index, question ->
                            item {
                                question.questionText?.let {
                                    Text(
                                        text = "${index + 1}. $it",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color(0xFF555555)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBA68C8)
                        )
                    ) {
                        Text(
                            text = "Zatvori",
                            fontFamily = FontFamily.SansSerif,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

