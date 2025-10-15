package hr.foi.rampu.menzamatefrontend.navigation

import hr.foi.rampu.menzamatefrontend.navigation.components.HomePage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Text
import hr.foi.rampu.menzamatefrontend.navigation.components.*
import hr.foi.rampu.menzamatefrontend.viewmodels.AppViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.FavoriteMenuViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.HomePageViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.RatingViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.SurveyViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import hr.foi.rampu.menzamatefrontend.navigation.components.menu.FavoriteMenuScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.menu.MenuDetailsScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.menu.MenuHistoryScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.menu.TopMenusScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.survey.CreateSurveyScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.survey.EmployeeSurveyScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.survey.SurveyScreen
import hr.foi.rampu.menzamatefrontend.navigation.components.survey.SurveyResultScreen
import hr.foi.rampu.menzamatefrontend.viewmodels.EmployeeSurveyViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.EmployeeSurveyViewModelFactory
import hr.foi.rampu.menzamatefrontend.viewmodels.MenuHistoryViewModel
import hr.foi.rampu.menzamatefrontend.viewmodels.TopMenusViewModel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AppNavHost(
    navController: NavHostController,
    appViewModel: AppViewModel,
    homeViewModel: HomePageViewModel = viewModel()
) {
    val ratingViewModel: RatingViewModel = viewModel()
    val userRole by appViewModel.userRole.collectAsState()

    Box(
        modifier = androidx.compose.ui.Modifier
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
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomePage(appViewModel = appViewModel, navController = navController)
            }

            composable(Screen.Favorites.route) {
                val favoriteMenuViewModel: FavoriteMenuViewModel = viewModel()
                FavoriteMenuScreen(
                    userId = appViewModel.getUserId(),
                    favoriteMenuViewModel = favoriteMenuViewModel,
                    navController = navController
                )
            }

            composable(Screen.Saved.route) {
                val surveyViewModel: SurveyViewModel = viewModel(
                    factory = AppViewModel.SurveyViewModelFactory(appViewModel.getUserId())
                )
                SurveyScreen(
                    navController = navController,
                    surveyViewModel = surveyViewModel,
                    userId = appViewModel.getUserId()
                )
            }

            composable(Screen.History.route) {
                val menuHistoryViewModel: MenuHistoryViewModel = viewModel()
                MenuHistoryScreen(
                    userId = appViewModel.getUserId(),
                    menuHistoryViewModel = menuHistoryViewModel,
                    navController = navController
                )
            }

            composable(Screen.TopMenus.route) {
                val topMenusViewModel: TopMenusViewModel = viewModel()
                TopMenusScreen(viewModel = topMenusViewModel)
            }

            composable(Screen.Profile.route) {
                val userId = appViewModel.getUserId()
                ProfileScreen(userId = userId)
            }

            composable(Screen.Details.route) { backStackEntry ->
                val menuId = backStackEntry.arguments?.getString("menuId")?.toIntOrNull()
                    ?: return@composable
                val menus = homeViewModel.menus.collectAsState().value
                val menu = menus.find { it.id == menuId }

                if (menu == null) {
                    Text(text = "Menu not found")
                } else {
                    MenuDetailsScreen(
                        menu = menu,
                        navController = navController,
                        ratingViewModel = ratingViewModel,
                        appViewModel = appViewModel
                    )
                }
            }

            if (userRole.equals("Employee", ignoreCase = true)) {
                composable(Screen.EmployeeSurveys.route) {
                    val userId = appViewModel.getUserId()
                    EmployeeSurveyScreen(navController = navController, userId = userId)
                }

                composable(Screen.EmployeeAnalytics.route) {
                    val employeeSurveyViewModel: EmployeeSurveyViewModel = viewModel(
                        factory = EmployeeSurveyViewModelFactory(appViewModel.getUserId())
                    )
                    SurveyResultScreen(
                        userId = appViewModel.getUserId(),
                        viewModel = employeeSurveyViewModel
                    )
                }
            }

            composable(Screen.AccessDenied.route) {
                AccessDeniedScreen(navController = navController)
            }

            composable(Screen.CreateSurvey.route) {
                CreateSurveyScreen(
                    navController = navController,
                    viewModel = viewModel(
                        factory = AppViewModel.SurveyViewModelFactory(appViewModel.getUserId())
                    )
                )
            }
        }
    }
}
