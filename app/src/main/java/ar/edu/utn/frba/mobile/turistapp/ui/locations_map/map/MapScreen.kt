import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.ui.audio_bar.AudioBar
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.MapViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListScreen
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListViewModelFactory
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.Title
import ar.edu.utn.frba.mobile.turistapp.ui.tour.TourViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.tour.TourViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(mapViewModel: MapViewModel, tourId: Int, navController: NavController? = null) {
    val tourViewModel: TourViewModel = viewModel(factory = TourViewModelFactory(tourId = tourId))
    val locationListViewModel: LocationListViewModel =
        viewModel(factory = LocationListViewModelFactory(tourId = tourId))
    val tourState = tourViewModel.tour.observeAsState()
    val tour = tourState.value
    val locations = locationListViewModel.locations.observeAsState().value
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        mapViewModel.startLocationUpdates(context)
    }

    DisposableEffect(key1 = mapViewModel) {
        onDispose {
            mapViewModel.stopLocationUpdates(context)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (tour != null) {
                        Text(
                            text = tour.title,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
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
            if (tour != null && locations != null) {
                Column(Modifier.fillMaxSize()) {
                    Box(Modifier.weight(1f)) {
                    BottomSheetScaffold(
                        mapScreen = { GoogleMapScreen(mapViewModel, locations) },
                        listTitle = { Title(name = stringResource(id = R.string.locations)) },
                        listContent = { LocationListScreen(tour, mapViewModel) }
                    ) }
                    AudioBar(
                        progress = 0.1f, // Valor de ejemplo para la barra de progreso (0.0f a 1.0f)
                        isPlaying = false, // Valor de ejemplo para el estado de reproducción
                        onPlayStopClick = { /* Lógica para alternar la reproducción/parada */ },
                        locations = { LocationListScreen(tour, mapViewModel) }
                    )
                }
            } else {
                Loading()
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


//********************** BOTTOM SHEET **********************//
//Code from official Jecpack Compose documentation page
@Composable
@ExperimentalMaterial3Api
fun BottomSheetScaffold(
    mapScreen: @Composable() () -> Unit,
    listTitle: @Composable() () -> Unit,
    listContent: @Composable() () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 60.dp,
        sheetSwipeEnabled = true,
        sheetContent = {
            Box(
                Modifier
                    .padding(0.dp),
                contentAlignment = Alignment.Center
            ) {
                listTitle()
            }
            Column(
                Modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                listContent()
            }
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            mapScreen()
        }

    }
}


//********************** PREVIEWS **********************//


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun BottomSheetScaffoldPreview() {
    BottomSheetScaffold(
        mapScreen = { Text(text = "Google Maps") },
        listTitle = { Text(text = stringResource(id = R.string.locations)) },
        listContent = {
            LocationListScreen(
                MockToursAPI.sampleTour(),
                MapViewModel()
            )
        }
    )
}
