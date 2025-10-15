package hr.foi.rampu.menzamatefrontend.navigation.components

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Saved : Screen("saved")
    object History : Screen("history")
    object Profile : Screen("profile")
    object TopMenus : Screen("top_menus")
    object Details : Screen("details/{menuId}") {
        fun createRoute(menuId: Int) = "details/$menuId"
    }

    object EmployeeSurveys : Screen("employee_surveys")
    object EmployeeAnalytics : Screen("employee_analytics")
    object CreateSurvey : Screen("create_survey")

    object AccessDenied : Screen("access_denied")
}

