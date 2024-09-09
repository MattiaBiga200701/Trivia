package com.example.trivia.ui.theme



import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.trivia.R

val MyCustomFont = FontFamily(
    Font(R.font.montserrat, FontWeight.Normal)
)

val MyTypography = Typography(

    bodyLarge = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

)


private val DarkColorScheme = darkColorScheme(

    primary = MyGreenDark,
    secondary = MyPinkDark,
    background = MyBlackDark,
    surface = MyBlackDark,
    error = MyRedDark,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = MyTextOnDarkBackground,
    onSurface = MyTextOnDarkSurface,
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(

    primary = MyGreen,
    secondary = MyPink,
    background = MyBlack,
    surface = MyBlack,
    error = MyRed,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)





@Composable
fun TriviaTheme(
    darkTheme: Boolean = false, // Default a false se non specificato
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyTypography,
        content = content
    )
}