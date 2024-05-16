package com.jeongg.ieum.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jeongg.ieum.R

val SuiteFamily = FontFamily(
    Font(R.font.black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.semi_bold, FontWeight.SemiBold, FontStyle.Normal),
)


// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 25.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = SuiteFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    )
)