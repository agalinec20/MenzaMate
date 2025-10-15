package hr.foi.rampu.menzamatefrontend.navigation.components.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import models.MenuHistoryResponse
import hr.foi.rampu.menzamatefrontend.navigation.components.Screen
import hr.foi.rampu.menzamatefrontend.viewmodels.MenuHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuHistoryScreen(
    userId: Int,
    menuHistoryViewModel: MenuHistoryViewModel,
    navController: NavHostController
) {
    val menuHistory by menuHistoryViewModel.menuHistory.collectAsState()

    LaunchedEffect(userId) {
        menuHistoryViewModel.loadMenuHistory(userId)
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
            content = { paddingValues ->
                if (menuHistory.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nemate povijest jelovnika.",
                            fontSize = 18.sp,
                            color = Color(0xFF333333),
                            fontFamily = FontFamily.SansSerif
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
                        items(menuHistory) { menu ->
                            MenuHistoryCard(
                                menu = menu,
                                navController = navController
                            )
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuHistoryCard(
    menu: MenuHistoryResponse,
    navController: NavHostController
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.Details.createRoute(menu.menuId)) }
            .padding(vertical = 4.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Color(0xFFBA68C8)),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        elevation = CardDefaults.outlinedCardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = menu.menuTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBA68C8),
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = menu.menuDescription,
                    fontSize = 14.sp,
                    color = Color(0xFF555555),
                    fontFamily = FontFamily.SansSerif
                )
            }
            IconButton(
                onClick = { navController.navigate(Screen.Details.createRoute(menu.menuId)) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Detalji",
                    tint = Color(0xFFBA68C8)
                )
            }
        }
    }
}
