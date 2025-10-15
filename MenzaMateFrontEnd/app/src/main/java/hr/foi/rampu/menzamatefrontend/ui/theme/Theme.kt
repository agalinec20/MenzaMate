package hr.foi.rampu.menzamatefrontend.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PurpleDark,
    secondary = PurpleLight,
    tertiary = GreyDark,
    background = Black,
    surface = GreyDark,
    onPrimary = White,
    onSecondary = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    secondary = PurpleLight,
    tertiary = GreyLight,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = PurplePrimary,
    onSurface = GreyDark
)

@Composable
fun MenzaMateFrontendTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
