package ar.edu.utn.frba.mobile.turistapp.ui.tour

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.MapViewModel
import coil.compose.AsyncImage


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TourScreen(tourId: Int, navController: NavController? = null) {
    val viewModel: TourViewModel = viewModel(factory = TourViewModelFactory(tourId = tourId))
    val tourState = viewModel.tour.observeAsState()
    val tour = tourState.value
    val isFavorite = remember {
        mutableStateOf(viewModel.isFavorite())
    }
    TourScreenView(tour, viewModel, isFavorite, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TourScreenView(tour: TourResponse?, viewModel: TourViewModel? = null, isFavorite: MutableState<Boolean> = mutableStateOf(false), navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text( "")
                },
                modifier = Modifier.border(1.dp, Color.Gray),
                navigationIcon = {
                    if (navController?.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel?.didTapHeart()
                        isFavorite.value = !isFavorite.value
                    }) {
                        var heartIcon = R.drawable.ic_heart
                        if (isFavorite.value) {
                            heartIcon = R.drawable.ic_heart_filled
                        }
                        Icon(
                            painter = painterResource(heartIcon),
                            contentDescription = null,
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            if (tour != null) {
                Tour(tour, navController)
            } else {
                Loading()
            }
        }
    }
}

@Composable
fun Tour(tour: TourResponse, navController: NavController? = null) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyColumn {
        item {
            Box(
                Modifier
                    .size(width = screenWidth, height = 250.dp)
                    .background(color = Color.Gray)
            ) {
                AsyncImage(
                    model = tour.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                IconButton(onClick = { navController?.navigate("map/${tour.id}") },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(y = (-8.dp), x = (-8.dp))
                        .size(55.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_play_circle_green),
                        contentDescription = stringResource(id = R.string.show_tour_locations),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(55.dp)
                    )
                }
            }
            Text(
                text = tour.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            Text(
                text = tour.description,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            Row() {
                Text(
                    text = tour.rating.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
                )
                Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(horizontal = 0.dp, vertical = 12.dp)) {
                    Text(
                        text = tour.ratingCount.toString() + " " + stringResource(R.string.ratings),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Stars(tour.rating)
                }
            }
            Text(
                text = stringResource(R.string.duration) + ": " + tour.duration,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            Text(
                text = stringResource(R.string.languages) + ": " + tour.languages.joinToString(" | "),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            Text(
                text = stringResource(R.string.locations),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            tour.locations.forEachIndexed { index, location ->
                Text(
                    text = (index+1).toString() + ". " + location,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
            Text(
                text = stringResource(R.string.reviews),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            tour.reviews.forEach { review ->
                Box(
                    Modifier.padding(horizontal = 10.dp, vertical = 0.dp)
                ) {
                    Stars(review.stars)
                }
                Text(
                    text = review.text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
                Box(
                    Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun Stars(number: Int) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        items(count = number) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                modifier = Modifier.height(18.dp)
            )
        }
        items(count = 5 - number) {
            Image(
                painter = painterResource(R.drawable.ic_star_empty),
                contentDescription = null,
                modifier = Modifier.height(18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TourScreenPreview() {
    TourScreenView(MockToursAPI.sampleTour())
}