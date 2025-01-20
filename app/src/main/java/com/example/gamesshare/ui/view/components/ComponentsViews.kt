package com.example.gamesshare.ui.view.components

import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.gamesshare.R
import com.example.gamesshare.ui.theme.gray8Color
import com.example.gamesshare.ui.theme.gray9Color
import com.example.gamesshare.ui.theme.roboto10Regular
import com.example.gamesshare.ui.theme.roboto16Medium
import com.example.gamesshare.ui.theme.roboto20Medium
import com.example.gamesshare.ui.theme.roboto9Regular
import com.example.gamesshare.ui.theme.rubik20sp
import com.example.gamesshare.ui.theme.text36Sp
import com.example.gamesshare.ui.view.screens.login.RegisterViewModel
import com.example.gamesshare.utils.DimensDp

@Composable
fun WavesBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x8AAFAFAF))
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.BottomCenter)
            ) {
                val wavePath1 = Path().apply {
                    moveTo(0f, 150f)
                    quadraticBezierTo(size.width * 0.25f, 200f, size.width * 0.5f, 150f)
                    quadraticBezierTo(size.width * 0.75f, 100f, size.width, 150f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(
                    path = wavePath1,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x992F2A2A),
                            Color(0x99AFADAD)
                        )
                    )
                )

                val wavePath2 = Path().apply {
                    moveTo(0f, 100f)
                    quadraticBezierTo(size.width * 0.25f, 50f, size.width * 0.5f, 100f)
                    quadraticBezierTo(size.width * 0.75f, 150f, size.width, 100f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(
                    path = wavePath2,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99281F1F),
                            Color(0x99A5A4A4)
                        )
                    ),
                    alpha = 0.8f
                )
            }
            content()
        }
    }
}

@Composable
fun IndicationPage(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) Color.DarkGray else Color.White
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)
            )
        }
    }
}

@Composable
fun GenericButton(title: String, isEnable: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = isEnable,
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            disabledContainerColor = Color.LightGray
        )
    ) {
        Text(text = title, style = roboto16Medium)
    }
}

@Composable
fun TextWithArrow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            style = roboto16Medium,
            color = Color.DarkGray
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp),
            tint = Color.DarkGray
        )
    }
}

@Composable
fun HeaderTextAndImage(titleText: String, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (title, image) = createRefs()

        Image(
            painter = painterResource(R.drawable.logo),
            null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .alpha(.7f),
            contentScale = ContentScale.Fit
        )

        Text(
            titleText,
            style = text36Sp,
            modifier = Modifier.constrainAs(title) {
                bottom.linkTo(image.bottom, margin = 16.dp)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun CoilImage(imageUri: String, modifier: Modifier) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = imageUri,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
    ) {
        val state = painter.state
        when (state) {
            AsyncImagePainter.State.Empty -> {

            }

            is AsyncImagePainter.State.Error -> {
                Toast.makeText(
                    context,
                    "Error al cargar la imagen intenta con otra",
                    Toast.LENGTH_LONG
                ).show()
            }

            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}


@Composable
fun LabelsValidations(mainViewModel: RegisterViewModel) {
    val validations = listOf(
        stringResource(R.string.validate_numbers) to mainViewModel.numbers,
        stringResource(R.string.validate_uppercase) to mainViewModel.upper,
        stringResource(R.string.validate_special_character) to mainViewModel.specialCharacter,
        stringResource(R.string.validate_three_numbers_in_sequence) to mainViewModel.threeConsecutive,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        validations.forEach { (label, value) ->
            ValidationsRequest(label, value)
        }
    }
}

@Composable
private fun ValidationsRequest(title: String, status: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(getImage(status)), null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(title, style = roboto10Regular)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun getImage(status: Boolean): Int {
    return if (status) com.example.customtextfield.R.drawable.ic_success else com.example.customtextfield.R.drawable.ic_error
}

@Composable
fun HeaderHomeInfo(imageUri: String, email: String, userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(Color(0x99000000))
    ) {
        Row(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    "Bienvenido",
                    style = roboto20Medium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (imageUri.isNotEmpty()) {
                        CoilImage(
                            imageUri,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Column {
                        Text(
                            userName,
                            style = roboto16Medium,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            email,
                            style = roboto10Regular,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                AddOptions(Icons.Outlined.Favorite, "Favoritos") {

                }
                AddOptions(Icons.Outlined.Search, "Buscar") {

                }
            }
        }
    }
}

@Composable
private fun AddOptions(imageVector: ImageVector, nameString: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {
            onClick()
        }, modifier = Modifier.size(24.dp)) {
            Image(
                imageVector = imageVector,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color(0xB3FAFAFA))
            )
        }
        Text(nameString, style = roboto10Regular, color = Color.White)
    }
}


@Composable
fun GridItemHomeScreen(
    imageUri: String,
    nameGame: String,
    platForm: List<String>,
    onClickShowVideo: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(128.dp)
            .height(200.dp)
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GetImage(
                imageUri,
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0x99000000))
                    .padding(8.dp)
            ) {
                GetPlatformImage(platForm)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    nameGame,
                    style = roboto10Regular,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .clickable { onClickShowVideo() }
                            .clip(shape = RoundedCornerShape(16.dp))
                            .border(
                                1.dp,
                                color = Color(0x99FAFAFA),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Mas datos",
                            style = roboto9Regular,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GetPlatformImage(platforms: List<String>) {
    val principalList = platforms
        .distinctBy { it.substringBefore(" ") }.filter {
            it.contains("PC")
                    || it.contains("PlayStation")
                    || it.contains("Xbox")
                    || it.contains("Nintendo")
                    || it.contains("Android")
                    || it.contains("Apple")
        }.take(3)

    Row {
        if (principalList.isNotEmpty()) {
            principalList.forEach {
                IconPlatform(it)
                Spacer(modifier = Modifier.width(DimensDp.DP8))
            }
        } else IconPlatform("0")
    }
}


@Composable
private fun IconPlatform(platforms: String) {

    val icon = when {
        platforms.contains("PC") -> R.drawable.ic_pc
        platforms.contains("PlayStation") -> R.drawable.ic_play
        platforms.contains("Xbox") -> R.drawable.ic_xbox
        platforms.contains("Nintendo") -> R.drawable.ic_nintendo
        platforms.contains("Android") -> R.drawable.ic_android
        platforms.contains("Apple") -> R.drawable.ic_apple
        else -> R.drawable.ic_game_des
    }

    Image(
        painter = painterResource(icon),
        "",
        modifier = Modifier.size(10.dp),
        colorFilter = ColorFilter.tint(Color.White)
    )
}


@Composable
fun GetImage(imageUri: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = imageUri,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier, alignment = Alignment.Center
    ) {
        val state = painter.state
        when (state) {
            AsyncImagePainter.State.Empty -> Unit

            is AsyncImagePainter.State.Error -> Unit

            is AsyncImagePainter.State.Loading -> {
                ShimmerEffectBox(
                    modifier =
                    Modifier
                        .fillMaxSize()
                )
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}


@Composable
fun CardLessSkeleton() {
    Card(
        modifier = Modifier
            .width(128.dp)
            .height(200.dp)
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ShimmerEffectBox(
                modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
            ShimmerEffectBox(
                modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
private fun ShimmerEffectBox(
    modifier: Modifier = Modifier
) {

    val shimmerColors = listOf(
        gray8Color,
        gray9Color
    )

    val transition = rememberInfiniteTransition(label = "")

    val animatedColor by transition.animateColor(
        initialValue = shimmerColors[0],
        targetValue = shimmerColors[1],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = modifier.background(animatedColor)
    )
}

@Composable
fun ComposableLifeCycle(
    lifeCycle: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {
    DisposableEffect(key1 = lifeCycle) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }

        lifeCycle.lifecycle.addObserver(observer)

        onDispose {
            lifeCycle.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun MainImage(image: String, modifier: Modifier) {
    val newImage = rememberImagePainter(data = image)

    Image(
        painter = newImage, contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}


@Composable
fun HeaderDetail(gameGame: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(Color(0x99000000))
            .padding(vertical = 16.dp , horizontal = 8.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(imageVector = Icons.Default.ArrowBack, "", tint = Color.White)
            }
            Text(
                gameGame,
                style = rubik20sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

enum class QualityMovies {
    High, Low
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListMoviesItems(imageUri: String, nameGame: String, showVide: (QualityMovies) -> Unit) {
    val newImage = rememberAsyncImagePainter(model = imageUri)
    Row(
        modifier = Modifier
            .height(88.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = newImage, contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                nameGame,
                style = roboto16Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                GenericButtonOutLine("low quality") {
                    showVide(QualityMovies.Low)
                }
                Spacer(modifier = Modifier.width(16.dp))
                GenericButtonOutLine("high quality") {
                    showVide(QualityMovies.High)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
        }
    }
}

@Composable
private fun GenericButtonOutLine(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable { onClick() }
            .clip(shape = RoundedCornerShape(16.dp))
            .border(
                1.dp,
                color = Color(0x99000000),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = roboto9Regular,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
        )
    }
}