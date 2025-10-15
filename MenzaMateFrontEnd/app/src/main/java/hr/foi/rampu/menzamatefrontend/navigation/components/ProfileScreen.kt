package hr.foi.rampu.menzamatefrontend.navigation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.FavoriteMenuViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: Int
) {
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel()
    val userState by profileViewModel.user.collectAsState()

    val favoriteMenuViewModel: FavoriteMenuViewModel = viewModel()
    val favorites by favoriteMenuViewModel.favoriteMenus.collectAsState()

    LaunchedEffect(userId) {
        profileViewModel.getUserData(userId)
        favoriteMenuViewModel.loadFavorites(userId)
    }

    var editedUsername by remember { mutableStateOf("") }
    LaunchedEffect(userState) {
        if (userState != null && editedUsername.isEmpty()) {
            editedUsername = userState!!.username
        }
    }

    val lastFavorite = favorites.lastOrNull()

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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Vaš profil",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = FontFamily.SansSerif
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (userState == null) {
                                Text(
                                    text = "Učitavanje podataka...",
                                    fontFamily = FontFamily.SansSerif
                                )
                            } else {
                                OutlinedTextField(
                                    value = editedUsername,
                                    onValueChange = { editedUsername = it },
                                    label = {
                                        Text(
                                            "Ime i prezime",
                                            fontFamily = FontFamily.SansSerif
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        profileViewModel.updateUserData(userId, editedUsername)
                                        Toast.makeText(
                                            context,
                                            "Podaci uspješno spremljeni",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.align(Alignment.End),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8))
                                ) {
                                    Text(
                                        text = "Spremi",
                                        color = Color.White,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                }
                            }
                        }
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (lastFavorite != null) {
                                Text(
                                    text = "Posljednje dodano omiljeno jelo:",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = FontFamily.SansSerif
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = lastFavorite.menuTitle,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = lastFavorite.menuDescription,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.SansSerif
                                )
                            } else {
                                Text(
                                    text = "Još niste dodali nijedno jelo u favorite.",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
