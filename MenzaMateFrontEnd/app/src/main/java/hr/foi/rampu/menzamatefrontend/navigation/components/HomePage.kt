package hr.foi.rampu.menzamatefrontend.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Menu
import hr.foi.rampu.menzamatefrontend.viewmodels.AppViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.HomePageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.rampu.menzamatefrontend.utils.formatDate
import androidx.navigation.NavHostController

@Composable
fun HomePage(
    appViewModel: AppViewModel,
    homeViewModel: HomePageViewModel = viewModel(),
    navController: NavHostController
) {
    val filteredMenus by homeViewModel.filteredMenus.collectAsState()

    LaunchedEffect(appViewModel.filterCriteria) {
        appViewModel.filterCriteria.collect { criteria ->
            if (criteria != null) {
                homeViewModel.applyFilter(criteria.mealType, criteria.date, criteria.excludeSimilar)
            } else {
                homeViewModel.applyFilter(null, null, false)
            }
        }
    }

    val menusGroupedByDate = filteredMenus.groupBy { formatDate(it.createdDate) }

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            menusGroupedByDate.forEach { (date, menus) ->
                item {
                    DateHeader(date)
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(menus) { menu ->
                            MenuCard(menu = menu, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateHeader(date: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = Color(0xFFBA68C8).copy(alpha = 0.8f),
        tonalElevation = 4.dp,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif
            ),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun MenuCard(menu: Menu, navController: NavHostController) {
    Card(
        onClick = { navController.navigate(Screen.Details.createRoute(menu.id)) },
        modifier = Modifier
            .width(320.dp)
            .height(180.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFCE93D8),
                                Color(0xFFBA68C8)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = getFoodEmoji(menu),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontFamily = FontFamily.SansSerif
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = menu.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        ),
                        color = Color.White,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = menu.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.SansSerif
                    ),
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 3
                )
            }
            IconButton(
                onClick = { navController.navigate(Screen.Details.createRoute(menu.id)) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = Color.White
                )
            }
        }
    }
}

fun getFoodEmoji(menu: Menu): String {
    val menuText = (menu.title + " " + menu.description).lowercase()
    val emojiMap = listOf(
        "juha od kostiju" to "🍲",
        "pečeno" to "🍖",
        "pljeskavica" to "🍖",
        "meso" to "🍖",
        "rižoto" to "🍚",
        "pileći" to "🍗",
        "piletina" to "🍗",
        "vege" to "🥦",
        "jela na narudžbu" to "🍽️",
        "riba" to "🐟",
        "oslić" to "🐟",
        "riblji" to "🐟",
        "pizza" to "🍕",
        "burger" to "🍔",
        "pomfrit" to "🍟",
        "salata" to "🥗",
        "špageti" to "🍝",
        "tjestenina" to "🍝",
        "desert" to "🍰",
        "kolač" to "🍰"
    )
    for ((keyword, emoji) in emojiMap) {
        if (menuText.contains(keyword)) {
            return emoji
        }
    }
    val fallbackEmojis = listOf("🍕", "🍔", "🍟", "🌭", "🍿", "🍣", "🍱", "🥗", "🍜", "🍰")
    val index = (menu.id.hashCode() and 0x7fffffff) % fallbackEmojis.size
    return fallbackEmojis[index]
}
