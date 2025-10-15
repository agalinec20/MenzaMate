package hr.foi.rampu.menzamatefrontend.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import hr.foi.rampu.menzamatefrontend.R
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController, userRole: String) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFFE1BEE7)
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    tint = if (currentRoute == Screen.Home.route) Color(0xFFBA68C8) else Color.White
                )
            }
        )

        if (userRole.equals("Employee", ignoreCase = true)) {
            NavigationBarItem(
                selected = currentRoute == Screen.EmployeeSurveys.route,
                onClick = {
                    navController.navigate(Screen.EmployeeSurveys.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_survey_management),
                        contentDescription = "Upravljanje Anketama",
                        tint = if (currentRoute == Screen.EmployeeSurveys.route) Color(0xFFBA68C8) else Color.White
                    )
                }
            )
        }
        if (userRole.equals("Student", ignoreCase = true)) {
            NavigationBarItem(
                selected = currentRoute == Screen.Favorites.route,
                onClick = {
                    navController.navigate(Screen.Favorites.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "Favoriti",
                        tint = if (currentRoute == Screen.Favorites.route) Color(0xFFBA68C8) else Color.White
                    )
                }
            )
        }

        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Profil",
                    tint = if (currentRoute == Screen.Profile.route) Color(0xFFBA68C8) else Color.White
                )
            }
        )
    }
}
