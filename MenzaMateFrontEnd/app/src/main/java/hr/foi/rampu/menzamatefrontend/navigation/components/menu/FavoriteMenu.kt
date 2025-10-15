package hr.foi.rampu.menzamatefrontend.navigation.components.menu

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import models.FavoriteMenuResponse
import hr.foi.rampu.menzamatefrontend.navigation.components.Screen
import hr.foi.rampu.menzamatefrontend.viewmodels.FavoriteMenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMenuScreen(
    userId: Int,
    favoriteMenuViewModel: FavoriteMenuViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val favoriteMenus by favoriteMenuViewModel.favoriteMenus.collectAsState()

    LaunchedEffect(userId) {
        favoriteMenuViewModel.loadFavorites(userId)
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
                if (favoriteMenus.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nemate omiljenih jelovnika.",
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
                        items(favoriteMenus) { favorite ->
                            FavoriteMealCard(
                                favorite = favorite,
                                onRemoveClick = {
                                    favoriteMenuViewModel.removeFavorite(userId, favorite.menuId)
                                    Toast.makeText(
                                        context,
                                        "Uspješno uklonjeno iz favorita",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
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
fun FavoriteMealCard(
    favorite: FavoriteMenuResponse,
    onRemoveClick: () -> Unit,
    navController: NavHostController
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.Details.createRoute(favorite.menuId)) },
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
                    text = favorite.menuTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBA68C8),
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = favorite.menuDescription,
                    fontSize = 14.sp,
                    color = Color(0xFF555555),
                    fontFamily = FontFamily.SansSerif
                )
            }
            Button(
                onClick = onRemoveClick,
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8))
            ) {
                Text(
                    text = "Ukloni",
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}
