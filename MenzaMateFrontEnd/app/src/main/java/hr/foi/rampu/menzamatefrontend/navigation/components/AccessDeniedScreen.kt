package hr.foi.rampu.menzamatefrontend.navigation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hr.foi.rampu.menzamatefrontend.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessDeniedScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pristup Odbijen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_foreground),
                            contentDescription = "Povratak"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nemate dozvolu za pristup ovom dijelu aplikacije.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}
