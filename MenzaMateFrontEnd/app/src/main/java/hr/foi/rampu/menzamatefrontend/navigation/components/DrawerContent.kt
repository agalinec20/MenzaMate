package hr.foi.rampu.menzamatefrontend.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.rampu.menzamatefrontend.R
@Composable
fun DrawerContent(
    currentScreen: String,
    onItemSelected: (Screen) -> Unit,
    onLogout: () -> Unit,
    userRole: String
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(235.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3E5F5),
                        Color(0xFFE1BEE7)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFBA68C8))
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hamburger),
                    contentDescription = "Hamburger",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MenzaMate",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Justify,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (userRole.equals("Student", ignoreCase = true)){
            DrawerItem(
                text = "Favoriti",
                iconId = R.drawable.ic_favorite,
                isSelected = currentScreen == Screen.Favorites.route,
                onClick = { onItemSelected(Screen.Favorites) }
            )
            DrawerItem(
                text = "Top 10",
                iconId = R.drawable.ic_trophy,
                isSelected = currentScreen == Screen.TopMenus.route,
                onClick = { onItemSelected(Screen.TopMenus) }
            )
            DrawerItem(
                text = "Ankete",
                iconId = R.drawable.ic_survey,
                isSelected = currentScreen == Screen.Saved.route,
                onClick = { onItemSelected(Screen.Saved) }
            )
            DrawerItem(
                text = "Povijest",
                iconId = R.drawable.ic_history,
                isSelected = currentScreen == Screen.History.route,
                onClick = { onItemSelected(Screen.History) }
            )
        } else {
            DrawerItem(
                text = "Ankete",
                iconId = R.drawable.ic_survey_management,
                isSelected = currentScreen == Screen.EmployeeSurveys.route,
                onClick = { onItemSelected(Screen.EmployeeSurveys) }
            )
            DrawerItem(
                text = "Analitika",
                iconId = R.drawable.ic_analytics,
                isSelected = currentScreen == Screen.EmployeeAnalytics.route,
                onClick = { onItemSelected(Screen.EmployeeAnalytics) }
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        DrawerItem(
            text = "Odjava",
            iconId = R.drawable.ic_logout,
            isSelected = false,
            onClick = { onLogout() }
        )
    }
}

@Composable
fun DrawerItem(
    text: String,
    iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFCE93D8),
                        Color(0xFFBA68C8)
                    )
                ))) {
            Text(
                text = text,
                fontSize = 18.sp,
                color = if (isSelected) Color(0xFFFCE4EC) else Color.White,
                fontFamily = FontFamily.SansSerif,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = text,
                tint = if (isSelected) Color(0xFFFCE4EC) else Color.White,
                modifier = Modifier
                    .size(38.dp)
                    .align(Alignment.CenterStart)
                    .padding(start = 12.dp)
            )
        }
    }
}

