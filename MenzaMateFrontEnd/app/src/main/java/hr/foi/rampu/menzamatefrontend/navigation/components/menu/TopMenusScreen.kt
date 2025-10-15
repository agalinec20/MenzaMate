package hr.foi.rampu.menzamatefrontend.navigation.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.TopMenus
import hr.foi.rampu.menzamatefrontend.viewmodels.TopMenusViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenusScreen(viewModel: TopMenusViewModel = viewModel()) {
    val topMenus by viewModel.topMenus.collectAsState()

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
            content = { paddingValues ->
                if (topMenus.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Nema dostupnih jelovnika.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF333333)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(topMenus) { menu ->
                            TopMenuItem(menu = menu)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun TopMenuItem(menu: TopMenus) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = menu.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Prosječna ocjena: ${menu.averageRating} (${menu.ratingCount} ${if (menu.ratingCount == 1) "glas" else "glasova"})",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF555555)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menu.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF555555)
            )
        }
    }
}
