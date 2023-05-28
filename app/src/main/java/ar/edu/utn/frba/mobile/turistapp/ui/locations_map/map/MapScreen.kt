import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPIWithRetrofit
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListScreen
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListViewModelFactory
import ar.edu.utn.frba.mobile.turistapp.ui.tour.TourViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.tour.TourViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(tourId: Int, navController: NavController? = null) {
    val tourViewModel: TourViewModel = viewModel(factory = TourViewModelFactory(tourId = tourId))
    val locationListViewModel: LocationListViewModel = viewModel(factory = LocationListViewModelFactory(tourId = tourId, LocalContext.current))
    val tourState = tourViewModel.tour.observeAsState()
    val tour = tourState.value
    val locations = locationListViewModel.locations.observeAsState().value
    MapsScreenView(tour, locations, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreenView(tour: TourResponse?, locations: List<Location>?, navController: NavController? = null) {
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
                MapDescription(tour, locations, navController)
            } else {
                Loading()
            }
        }
    }
}

@Composable
fun MapDescription(tour: TourResponse, locations: List<Location>, navController: NavController? = null) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LazyColumn {
        item {
            Box(
                Modifier.size(width = screenWidth, height = 250.dp)
                    .background(color = Color.Gray)
            ) {
                //  GoogleMapsScreen() <-- google maps
            }
        }
        item {
            Box(
                Modifier.size(width = screenWidth, height = 250.dp)
                    .background(color = Color.Gray)
            ) {
                LocationListScreen(locations)
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

//********************** PREVIEWS **********************//

@Composable
@Preview(showBackground = true)
fun MapScreenPreview() {
    MapsScreenView(MockToursAPI.sampleTour(), LocationAPIWithRetrofit.sampleLocations())
}
