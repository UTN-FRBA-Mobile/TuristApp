package ar.edu.utn.frba.mobile.turistapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.core.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.Tour

@Composable
fun HomeScreen(viewModel: ToursViewModel = viewModel(), navController: NavController? = null) {
    val toursState = viewModel.tours.observeAsState()
    val tours = toursState.value
    AppScaffold(navController = navController) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (tours != null) {
                Tours(tours)
            } else {
                Loading()
            }
        }
    }
}

@Composable
fun Tours(tours: Array<Tour>) {
    LazyColumn {
        tours.forEach { tour ->
            item { TourRow(tour) }
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
    Tours(MockToursAPI.sampleTours())
}