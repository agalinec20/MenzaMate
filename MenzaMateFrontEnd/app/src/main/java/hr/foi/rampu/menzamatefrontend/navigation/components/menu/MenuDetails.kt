package hr.foi.rampu.menzamatefrontend.navigation.components.menu

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import hr.foi.rampu.menzamatefrontend.R
import hr.foi.rampu.menzamatefrontend.viewmodels.*
import models.Menu
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDetailsScreen(
    menu: Menu,
    navController: NavHostController,
    ratingViewModel: RatingViewModel,
    appViewModel: AppViewModel
) {
    val context = LocalContext.current

    val ratings by ratingViewModel.ratings.collectAsState()
    val averageRating by ratingViewModel.averageRating.collectAsState()
    val ratingCount by ratingViewModel.ratingCount.collectAsState()

    var userRating by remember { mutableIntStateOf(0) }
    var userComment by remember { mutableStateOf("") }
    var showAllComments by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    val userId = appViewModel.getUserId()

    val favoriteMenuViewModel: FavoriteMenuViewModel = viewModel()
    val menuHistoryViewModel: MenuHistoryViewModel = viewModel()

    // Animacija skaliranja ikone
    var showFavoriteAnimation by remember { mutableStateOf(false) }

    // Kad se showFavoriteAnimation promijeni u true, nakon kratke pauze vratimo na false
    LaunchedEffect(showFavoriteAnimation) {
        if (showFavoriteAnimation) {
            delay(300) // Trajanje vidljive animacije
            showFavoriteAnimation = false
        }
    }

    // Definiramo trenutno stanje scale animacije
    val scale by animateFloatAsState(
        targetValue = if (showFavoriteAnimation) 1.3f else 1f,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(menu.id) {
        ratingViewModel.fetchRatingsForMenu(menu.id)
        ratingViewModel.fetchAverageRating(menu.id)
        menuHistoryViewModel.addHistory(userId, menu.id)
        favoriteMenuViewModel.loadFavorites(userId)
    }
    val favoriteMenus by favoriteMenuViewModel.favoriteMenus.collectAsState()
    LaunchedEffect(favoriteMenus) {
        isFavorite = favoriteMenus.any { it.menuId == menu.id }
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
            topBar = {
                TopAppBar(
                    title = { Text(text = menu.title, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back_foreground),
                                contentDescription = "Natrag",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            if (!isFavorite) {
                                // Ako dodajemo u favorite, pokrećemo animaciju
                                favoriteMenuViewModel.addFavorite(userId, menu.id)
                                Toast.makeText(context, "Dodano u favorite", Toast.LENGTH_SHORT).show()
                                showFavoriteAnimation = true
                            } else {
                                favoriteMenuViewModel.removeFavorite(userId, menu.id)
                                Toast.makeText(context, "Uklonjeno iz favorita", Toast.LENGTH_SHORT).show()
                            }
                            favoriteMenuViewModel.loadFavorites(userId)
                        }) {
                            // Omotavamo ikonu s transformom scale
                            Icon(
                                painter = painterResource(
                                    id = if (isFavorite) R.drawable.ic_favorite_border_fill
                                    else R.drawable.ic_favorite_border
                                ),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .size(28.dp)
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale
                                    ),
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFBA68C8)
                    )
                )
            },
            containerColor = Color.Transparent,
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = menu.title,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF333333)
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = menu.description,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFF555555)
                                    )
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                if (ratingCount > 0) {
                                    Text(
                                        text = "Prosječna ocjena: ${String.format("%.1f", averageRating)} ($ratingCount ocjena)",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF333333)
                                        )
                                    )
                                } else {
                                    Text(
                                        text = "Nema ocjena za ovaj meni.",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF333333)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Divider(color = Color(0xFFCCCCCC))
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Vaša ocjena",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF333333)
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    (1..5).forEach { star ->
                                        Icon(
                                            painter = painterResource(
                                                id = if (userRating >= star) R.drawable.ic_star_filled
                                                else R.drawable.ic_star_outline
                                            ),
                                            contentDescription = "Zvjezdica $star",
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clickable { userRating = star },
                                            tint = Color(0xFFFFC107)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = userComment,
                                    onValueChange = { userComment = it },
                                    label = { Text("Vaš komentar") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        ratingViewModel.submitRating(
                                            menuId = menu.id,
                                            userId = userId,
                                            rating = userRating,
                                            comment = if (userComment.isBlank()) null else userComment
                                        )
                                        userComment = ""
                                        userRating = 0
                                        ratingViewModel.fetchRatingsForMenu(menu.id)
                                    },
                                    enabled = userRating > 0,
                                    modifier = Modifier.align(Alignment.End),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8))
                                ) {
                                    Text(text = "Pošalji ocjenu", color = Color.White)
                                }
                            }
                        }
                    }

                    item {
                        Divider(color = Color(0xFFCCCCCC))
                    }

                    item {
                        Text(
                            text = "Recenzije korisnika",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                        )
                    }

                    if (ratings.isEmpty()) {
                        item {
                            Text(
                                text = "Još nema recenzija.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF555555)
                                )
                            )
                        }
                    } else {
                        items(if (showAllComments) ratings else ratings.take(3)) { rating ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Korisnik: ${rating.username}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF333333)
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Ocjena: ${rating.menuRating}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(0xFF333333)
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = rating.comment ?: "Nema komentara.",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color(0xFF555555)
                                        )
                                    )
                                }
                            }
                        }
                        if (ratings.size > 3) {
                            item {
                                Text(
                                    text = if (showAllComments) "Sakrij recenzije" else "Prikaži sve recenzije",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFFBA68C8),
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier
                                        .clickable { showAllComments = !showAllComments }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
