package com.example.gamesshare.ui.theme

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gamesshare.R

private val fontFamily = FontFamily(
    Font(R.font.rubik_vinyl_regular, FontWeight.Normal),
)

var text36Sp = TextStyle(
    fontSize = 36.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = fontFamily,
    lineHeight = 16.sp
)
var textLoader = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = fontFamily,
    lineHeight = 16.sp
)
var rubik14sp = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = fontFamily,
    lineHeight = 16.sp
)

var rubik20sp = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = fontFamily,
    lineHeight = 18.sp
)



private val robotoFontFamily = FontFamily(
    Font(com.example.customtextfield.R.font.roboto_regular, FontWeight.Normal),
    Font(com.example.customtextfield.R.font.roboto_bold, FontWeight.Bold),
    Font(com.example.customtextfield.R.font.roboto_light, FontWeight.Light),
    Font(com.example.customtextfield.R.font.roboto_medium, FontWeight.Medium)
)


var roboto16Medium = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontFamily = robotoFontFamily,
    fontWeight = FontWeight.Medium,
)
var roboto14Medium = TextStyle(
    fontSize = 14.sp,
    lineHeight = 24.sp,
    fontFamily = robotoFontFamily,
    fontWeight = FontWeight.Medium,
)
var roboto14Bold = TextStyle(
    fontSize = 14.sp,
    lineHeight = 24.sp,
    fontFamily = robotoFontFamily,
    fontWeight = FontWeight.Bold,
)
var roboto10Light = TextStyle(
    fontSize = 10.sp,
    fontFamily = robotoFontFamily,
    fontWeight = FontWeight.Light,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)
var roboto20Medium = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Medium,
    fontFamily = robotoFontFamily,
    lineHeight = 24.sp,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)
var roboto10Medium = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Medium,
    fontFamily = robotoFontFamily,
    lineHeight = 24.sp,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)
var roboto20Bold = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = robotoFontFamily,
    lineHeight = 24.sp,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)
var roboto14Regular = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = robotoFontFamily,
    lineHeight = 16.sp,
    platformStyle = PlatformTextStyle(includeFontPadding = false)
)
var roboto20Regular = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = robotoFontFamily,
    lineHeight = 16.sp
)
var roboto10Regular = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = robotoFontFamily,
    lineHeight = 16.sp
)

var roboto9Regular = TextStyle(
    fontSize = 8.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = robotoFontFamily,
    lineHeight = 16.sp
)
var roboto8Light = TextStyle(
    fontSize = 8.sp,
    fontWeight = FontWeight.Light,
    fontFamily = robotoFontFamily,
    lineHeight = 16.sp
)
