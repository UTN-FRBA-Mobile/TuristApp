package ar.edu.utn.frba.mobile.turistapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.core.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.ui.main.AppScaffold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ToursViewModel = viewModel(), navController: NavController? = null) {
    val toursState = viewModel.tours.observeAsState()
    val tours = toursState.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tours")
                }
            )
        }
    ) {
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
fun Tours(tours: Array<MinifiedTour>) {
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