package com.example.gamesshare.ui.view.screens.detail.views.detail

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gamesshare.R
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.models.RatingsValuesDomain
import com.example.gamesshare.domain.models.ShortScreenShotsDomain
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.ui.theme.roboto14Bold
import com.example.gamesshare.ui.theme.roboto14Medium
import com.example.gamesshare.ui.theme.roboto14Regular
import com.example.gamesshare.ui.theme.roboto8Light
import com.example.gamesshare.ui.theme.rubik20sp
import com.example.gamesshare.ui.view.components.BoxBackGround
import com.example.gamesshare.ui.view.components.CircularIndicatorDemo
import com.example.gamesshare.ui.view.components.ContentFastActions
import com.example.gamesshare.ui.view.components.GenericButton
import com.example.gamesshare.ui.view.components.GetImage
import com.example.gamesshare.ui.view.components.HeaderDetail
import com.example.gamesshare.ui.view.components.IndicationPage
import com.example.gamesshare.ui.view.components.ItemCommentsView
import com.example.gamesshare.ui.view.components.MainImage
import com.example.gamesshare.ui.view.components.MetaCriticComposeView
import com.example.gamesshare.ui.view.components.TypesFastActions
import com.example.gamesshare.ui.view.components.WavesBackground
import com.example.gamesshare.ui.view.screens.detail.observer.GetGamesObserver
import com.example.gamesshare.ui.view.screens.detail.observer.StatusUI
import com.example.gamesshare.utils.DimensDp.DP8
import com.example.gamesshare.utils.loader.DsLoaderView
import kotlin.math.absoluteValue

@Composable
fun getTitle(stateUI: StatusUI, gameName: String): String {
    return when (stateUI) {
        StatusUI.Await -> ""
        StatusUI.Error -> "Error!!"
        StatusUI.Success -> gameName
    }
}

@Composable
fun DetailGameScreen(
    viewModel: DetailGameViewModel = hiltViewModel(),
    sharePreferenceViewModel: SharePreferenceViewModel,
    game: GamesModel,
    navController: NavController,
    listMovies: () -> Unit
) {
    val userName = sharePreferenceViewModel.userName.collectAsState("")
    val userImage = sharePreferenceViewModel.userImage.collectAsState("")
    BackHandler { }

    val state by viewModel.observerData.collectAsState()
    val stateMovie by viewModel.observerState.collectAsState()
    val stateUI by viewModel.stateUI

    LaunchedEffect(Unit) {
        viewModel.fetchComments(game.gameId.toString())
    }

    LaunchedEffect(key1 = state.status) {
        if (state.status is StatusClass.Loading) {
            DsLoaderView.showLoader()
            viewModel.fetchData(game.gameId)
        }
    }


    LaunchedEffect(key1 = stateMovie.status) {
        if (stateMovie.status is StatusClass.Loading) {
            viewModel.fetchMovies(game.slug)
        }
    }

    GetGamesObserver(state = state) {
        viewModel.setUIState(it)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        HeaderDetail(getTitle(stateUI, game.gameName), navController = navController)
    }) { paddingValues ->
        WavesBackground(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            when (stateUI) {
                StatusUI.Await -> Unit
                StatusUI.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "Error Al cargar los datos",
                            style = rubik20sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        GenericButton("Reintentar", true) {
                            viewModel.resetFetchData()
                        }
                    }
                }

                StatusUI.Success -> {
                    ContentSuccess(
                        backgroundImage = game.backgroundImage,
                        description = state.data?.description ?: "",
                        list = stateMovie.data ?: emptyList(),
                        sharePreferenceViewModel = sharePreferenceViewModel,
                        viewModel = viewModel,
                        userName = userName.value,
                        userImage = userImage.value,
                        idGame = game.gameId.toString(),
                        listRatings = game.ratings,
                        listPlataforms = game.allPlatforms,
                        website = state.data?.website ?: "NA",
                        listScreenShots = game.short_screenshots,
                        topGame = game.top,
                        metacritic = game.metacritic
                    ) {
                        listMovies()
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentSuccess(
    idGame: String,
    userName: String,
    userImage: String,
    backgroundImage: String,
    description: String,
    list: List<GameMovieModel>,
    viewModel: DetailGameViewModel,
    sharePreferenceViewModel: SharePreferenceViewModel,
    listRatings: List<RatingsValuesDomain>,
    listPlataforms: List<String>,
    website: String,
    listScreenShots: List<ShortScreenShotsDomain>,
    topGame: Int,
    metacritic: Int,
    listMovies: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    val stateComments by viewModel.observerComments.collectAsState()
    val sheepHeight = getHalfScreenHeightInDp(context)
    val scroll = rememberScrollState(0)
    val pagerState = rememberPagerState(pageCount = {
        listScreenShots.size
    })

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val (image, detail, metacriticV, pageIndication, fastIcons) = createRefs()


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                MainImage(
                    image = listScreenShots[page].image,
                    modifier = Modifier
                )
            }
        }

        IndicationPage(
            pagerState.pageCount,
            pagerState.currentPage,
            modifier = Modifier.constrainAs(pageIndication){
                start.linkTo(image.start)
                end.linkTo(image.end)
                bottom.linkTo(image.bottom, margin = 8.dp)
            }, size = 10.dp
        )

        BoxBackGround(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(detail) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(
                        image.bottom, margin = 8.dp
                    )
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }, contentColor = Color(0xE6FAFAFA)
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item {
                    Text(
                        "Website",
                        modifier = Modifier.padding(start = 8.dp),
                        style = roboto14Regular,
                        color = Color.Gray
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            website,
                            style = roboto14Regular,
                            modifier = Modifier.weight(1f),
                            textDecoration = TextDecoration.Underline,
                            color = Color(0XFF1E90FF)
                        )

                        Icon(
                            imageVector = Icons.Outlined.CopyAll,
                            "",
                            modifier = Modifier
                                .size(20.dp)
                                .clip(
                                    CircleShape
                                )
                                .clickable { }, tint = Color.Gray
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        "Valoraciones",
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        style = roboto14Regular,
                        color = Color.Gray
                    )
                }
                items(listRatings) {
                    RatingsItemView(
                        recomentTitle = it.title,
                        votes = it.votes,
                        percent = it.percent,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        "Plataformas",
                        modifier = Modifier.padding(start = 8.dp),
                        style = roboto14Regular,
                        color = Color.Gray
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(listPlataforms) { platform ->
                    Text(
                        "●  $platform",
                        style = roboto14Regular,
                        color = Color(0xFF7A7A7A),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }



        MetaCriticComposeView(
            metacritic = metacritic,
            modifier = Modifier.constrainAs(metacriticV) {
                start.linkTo(image.start)
                bottom.linkTo(image.bottom)
            })

        ContentFastActions(
            modifier = Modifier.constrainAs(fastIcons) {
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            },
            showTrailer = list.isNotEmpty()
        ) {
            when (it) {
                TypesFastActions.COMMENTS -> {
                    showBottomSheet = true
                }

                TypesFastActions.DETAILS -> Unit
                TypesFastActions.TRAILERS -> {
                    sharePreferenceViewModel.listMovies = list
                    listMovies()
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.height(sheepHeight),
            containerColor = Color(0xFFFFFFFF),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${stateComments.size} comentarios",
                        style = roboto14Medium,
                        color = Color.Gray
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(rememberNestedScrollInteropConnection()),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 8.dp)
                ) {
                    items(stateComments) { comment ->
                        ItemCommentsView(
                            image = comment.userImage,
                            userName = comment.userName,
                            comment = comment.comment,
                            date = comment.date
                        )
                    }
                }
                GenericButton(title = "Crear", true) {
                    viewModel.createComments(
                        idGame = idGame,
                        userImage = userImage,
                        userName = userName,
                        comment = "Comment 1"
                    )
                }
            }
        }
    }
}


@Composable
fun RatingsItemView(recomentTitle: String, votes: Int, percent: Double) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 8.dp),
        ) {
            Text(recomentTitle, style = roboto14Bold)
            Image(
                painter = painterResource(getImageRating(recomentTitle)),
                null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .size(24.dp)
            )
        }
        DividerV()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(votes.toString(), style = roboto14Bold)
            Text("Votes", style = roboto8Light)
        }
        DividerV()
        CircularIndicatorDemo(percent, Modifier.weight(1f))
    }
}

fun getImageRating(value: String): Int {
    return when (value) {
        "exceptional" -> R.drawable.ic_excelent
        "recommended" -> R.drawable.ic_recomment
        "meh" -> R.drawable.ic_meh
        "skip" -> R.drawable.ic_bad
        else -> R.drawable.ic_recomment
    }
}

@Composable
private fun DividerV() {
    VerticalDivider(
        modifier = Modifier
            .width(1.dp)
            .height(80.dp)
            .background(Color.DarkGray)
    )
}

private fun getHalfScreenHeightInDp(context: Context): Dp {
    val displayMetrics = context.resources.displayMetrics
    val screenHeightInPixels = displayMetrics.heightPixels
    val density = displayMetrics.density
    return (screenHeightInPixels * 3 / 4 / density).dp
}

