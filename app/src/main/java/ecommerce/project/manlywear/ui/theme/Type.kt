package ecommerce.project.manlywear.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 40.sp
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 44.sp
    )
)