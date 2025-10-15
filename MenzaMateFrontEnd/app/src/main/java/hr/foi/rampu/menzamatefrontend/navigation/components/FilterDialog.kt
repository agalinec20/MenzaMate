package hr.foi.rampu.menzamatefrontend.navigation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hr.foi.rampu.menzamatefrontend.ui.theme.MenzaMateFrontendTheme

@Composable
fun FilterDialog(onDismiss: () -> Unit, onFilterSelected: (String?, String?, Boolean) -> Unit) {
    var selectedMealType by remember { mutableStateOf<String?>(null) }
    var dateFilter by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var excludeSimilar by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = { selectedDate ->
                dateFilter = selectedDate
                showDatePicker = false
            },
            onDismissRequest = { showDatePicker = false }
        )
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Filtriraj menije") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { selectedMealType = "Ručak" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedMealType == "Ručak") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            contentColor = if (selectedMealType == "Ručak") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Ručak", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { selectedMealType = "Večera" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedMealType == "Večera") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            contentColor = if (selectedMealType == "Večera") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Večera", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (dateFilter.isEmpty()) "Odaberi datum" else "Odabrani datum: $dateFilter")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = excludeSimilar,
                        onCheckedChange = { excludeSimilar = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ne prikazuj slične menije", fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onFilterSelected(selectedMealType, dateFilter, excludeSimilar)
            }) {
                Text("Primijeni filter")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Zatvori",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Composable
fun DatePickerDialog(onDateSelected: (String) -> Unit, onDismissRequest: () -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    DisposableEffect(Unit) {
        datePickerDialog.setOnCancelListener { onDismissRequest() }
        datePickerDialog.show()
        onDispose { }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterDialogPreview() {
    MenzaMateFrontendTheme {
        FilterDialog(
            onDismiss = {},
            onFilterSelected = { mealType, date, excludeSimilar -> }
        )
    }
}
