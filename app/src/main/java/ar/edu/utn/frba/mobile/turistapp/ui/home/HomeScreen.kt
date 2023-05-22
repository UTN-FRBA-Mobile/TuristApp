package ar.edu.utn.frba.mobile.turistapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ToursViewModel = viewModel(), navController: NavController? = null) {
    val nearbyToursState = viewModel.nearbyTours.observeAsState()
    val favoriteToursState = viewModel.favoriteTours.observeAsState()
    val nearbyTours = nearbyToursState.value
    val favoriteTours = favoriteToursState.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.tours))
                },
                modifier = Modifier.border(1.dp, Color.Gray)
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            if (nearbyTours != null && favoriteTours != null) {
                Tours(nearbyTours, favoriteTours, navController)
            } else {
                Loading()
            }
        }
    }
}

@Composable
fun Tours(nearbyTours: List<MinifiedTour>, favoriteTours: List<MinifiedTour>, navController: NavController? = null) {
    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.nearbyTours),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        nearbyTours.forEach { tour ->
            item { TourRow(tour, navController) }
        }
        item {
            Text(
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        favoriteTours.forEach { tour ->
            item { TourRow(tour, navController) }
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Tours(MockToursAPI.sampleTours(), MockToursAPI.sampleTours())
}