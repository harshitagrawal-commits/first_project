package com.example.jpc_library.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun Global_Theme_JPCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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




@Composable
fun Custom_Theme_JPC(
    themeIndex: Int,
    content: @Composable () -> Unit
) {
    val colors = when (themeIndex) {
        2 -> lightColorScheme(
            primary = Color(0xFFEA7068),
            background = Color(0xFFA47868),
            surface = Color(0xFF7C380B)
        )
        3 -> lightColorScheme(
            primary = Color(0xFF4CAF50),
            background = Color(0xFF095548),
            surface = Color(0xFF27670C)
        )
        else -> lightColorScheme(
            surface = Color(0xFF260460)
        ) // default theme
    }

    MaterialTheme(colorScheme = colors, content = content)
}


@Composable
fun StyledButton(
    text: String,
    colors: ButtonColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, colors = colors, modifier = modifier) {
        Text(text)
    }
}

@Composable
fun resolveButtonColors(isSelected: Boolean): ButtonColors =
    ButtonDefaults.buttonColors(
        containerColor =
            if (isSelected) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.primary,
        contentColor = Color.White

        //if (isSelected) MaterialTheme.colorScheme.onPrimary
        //else MaterialTheme.colorScheme.onSurface
    )