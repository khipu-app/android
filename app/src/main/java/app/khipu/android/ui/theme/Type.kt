package app.khipu.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import app.khipu.android.R

val firaMonoFamily = FontFamily(
    Font(R.font.firamono_regular),
    Font(R.font.firamono_medium, FontWeight.Medium),
    Font(R.font.firamono_bold, FontWeight.Bold)
)

val firaSansFamily = FontFamily(
    Font(R.font.firasans_regular),
    Font(R.font.firasans_medium, FontWeight.Medium),
    Font(R.font.firasans_bold, FontWeight.Bold)
)

val Typography = Typography(
    defaultFontFamily = firaMonoFamily
)

val screenTitle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    lineHeight = 28.8.sp,
    textAlign = TextAlign.Center,
    fontFamily = firaMonoFamily
)

val screenHeader = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    textAlign = TextAlign.Center,
    fontFamily = firaMonoFamily
)

val header1 = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontFamily = firaMonoFamily
)

val header2 = TextStyle(
    fontFamily = firaMonoFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    lineHeight = 21.6.sp
)

val title = TextStyle(
    fontFamily = firaMonoFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 19.2.sp
)

val paragraph = TextStyle(
    fontFamily = firaMonoFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 19.2.sp
)

val commented = TextStyle(
    fontFamily = firaSansFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 16.sp,
    textAlign = TextAlign.Center
)