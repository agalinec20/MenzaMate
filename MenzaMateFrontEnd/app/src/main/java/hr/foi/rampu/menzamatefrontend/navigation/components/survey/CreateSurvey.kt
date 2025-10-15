package hr.foi.rampu.menzamatefrontend.navigation.components.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hr.foi.rampu.menzamatefrontend.R
import hr.foi.rampu.menzamatefrontend.viewmodels.SurveyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSurveyScreen(
    navController: NavHostController,
    viewModel: SurveyViewModel
) {
    val surveyName = remember { mutableStateOf("") }
    val surveyDescription = remember { mutableStateOf("") }
    val questions = remember { mutableStateListOf<String>() }
    val newQuestion = remember { mutableStateOf("") }

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
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = surveyName.value,
                    onValueChange = { surveyName.value = it },
                    label = { Text("Naziv ankete", fontFamily = FontFamily.SansSerif) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = surveyDescription.value,
                    onValueChange = { surveyDescription.value = it },
                    label = { Text("Opis ankete", fontFamily = FontFamily.SansSerif) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Pitanja:",
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.SansSerif)
                )
                Column {
                    questions.forEachIndexed { index, question ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Pitanje ${index + 1}: $question",
                                style = MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily.SansSerif),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { questions.removeAt(index) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete_foreground),
                                    contentDescription = "Obriši pitanje"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                OutlinedTextField(
                    value = newQuestion.value,
                    onValueChange = { newQuestion.value = it },
                    label = { Text("Dodaj pitanje", fontFamily = FontFamily.SansSerif) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (newQuestion.value.isNotEmpty()) {
                            questions.add(newQuestion.value)
                            newQuestion.value = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8))
                ) {
                    Text("Dodaj pitanje", fontFamily = FontFamily.SansSerif)
                }
                Button(
                    onClick = {
                        if (surveyName.value.isNotEmpty() &&
                            surveyDescription.value.isNotEmpty() &&
                            questions.isNotEmpty()
                        ) {
                            viewModel.createSurveyWithQuestions(
                                surveyName.value,
                                surveyDescription.value,
                                questions.toList()
                            )
                            navController.navigateUp()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8))

                ) {
                    Text("Kreiraj", fontFamily = FontFamily.SansSerif)
                }
            }
        }
    }
}
