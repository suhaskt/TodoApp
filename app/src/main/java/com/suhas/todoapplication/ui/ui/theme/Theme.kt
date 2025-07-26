package com.suhas.todoapplication.ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)

//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

private val LightColorPalette = lightColors(
    primary = HoneyYellow,
    primaryVariant = GoldenYellow,
    secondary = BeeBlack,
    secondaryVariant = DarkBeeBlack,
    background = SoftYellow,
    surface = BeeWhite,
    error = ErrorRed,
    onPrimary = BeeBlack,
    onSecondary = BeeWhite,
    onBackground = BeeGray,
    onSurface = BeeBlack,
    onError = BeeWhite
)

private val SubtleColorPalette = lightColors(
    primary = SoftHoneyYellow,
    primaryVariant = GentleGoldenYellow,
    secondary = BeeGray,
    secondaryVariant = DarkBeeGray,
    background = VerySoftYellow,
    surface = BeeWhite,
    error = SubtleErrorRed,
    onPrimary = BeeBlack,
    onSecondary = BeeWhite,
    onBackground = BeeBlack,
    onSurface = BeeBlack,
    onError = BeeWhite
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colors =  LightColorPalette,
        typography = Typography,
        content = content
    )
}

/*val colorScheme = when {
       dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
           val context = LocalContext.current
           if (darkTheme)
               dynamicDarkColorScheme(context)
           else
               dynamicLightColorScheme(context)
       }

       darkTheme -> LightColorPalette
       else -> SubtleColorPalette
   }*/