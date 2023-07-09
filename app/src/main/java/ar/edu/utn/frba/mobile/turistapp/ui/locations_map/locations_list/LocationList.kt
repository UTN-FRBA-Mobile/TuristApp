package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPIWithRetrofit
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import ar.edu.utn.frba.mobile.turistapp.core.utils.AudioPlayer
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.MapViewModel
import com.google.android.gms.maps.model.LatLng


@Composable
fun LocationListScreen(tour: TourResponse, locations: List<Location>, viewModel: MapViewModel, audioPlayer: AudioPlayer) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        LocationList(tour, locations, viewModel, audioPlayer)
    }
}

@Composable
fun LocationList(tour: TourResponse, locations: List<Location>, viewModel: MapViewModel, audioPlayer: AudioPlayer) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 8.dp)
    ) {
        items(items = locations, itemContent = { location ->
            LocationCard(tour, location, viewModel, audioPlayer)
            Spacer(modifier = Modifier.height(12.dp))
        })
    }
}

@Composable
fun LocationCard(tour: TourResponse, location: Location, viewModel: MapViewModel, audioPlayer: AudioPlayer) {
    val isExpanded = remember { mutableStateOf(false) }
    val currentPosition = viewModel.currentLocation.collectAsState().value
    val playedAudiosOrderNumbers =  remember { mutableStateOf(IntArray(0)) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .clickable {
                isExpanded.value = !isExpanded.value
            } // toggles the expanded state on click
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 8.dp, vertical = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row {
                        Text(text = location.order.toString() + ".", style = MaterialTheme.typography.titleMedium)
                        Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                    }
                    Row {
                        Text(
                            text = if (currentPosition == null)
                                    ""
                                else
                                    location.distance(currentPosition).toInt().toString() + " m",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if(currentPosition != null && location.isNear(currentPosition)) {
                            Chip(text = "ahora cerca")
                            // Si estamos cerca de la posición actual y el audio del location no fue reproducido, play audio
                            if(!playedAudiosOrderNumbers.value.contains(location.order)) {
                                audioPlayer.playTourLocation(tour, location)
                                playedAudiosOrderNumbers.value = playedAudiosOrderNumbers.value + location.order
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center, modifier = Modifier.width(40.dp)) {
                    AudioButton(tour, currentPosition, location, audioPlayer)
                }
            }
            // If the card is expanded, show the description
            if (isExpanded.value) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = location.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// Creating a composable function to
// create two icon buttons namely play and pause
// Calling this function as content in the above function
@Composable
fun AudioButton(tour: TourResponse, currentPosition: LatLng?, location: Location, audioPlayer: AudioPlayer) {
    val playingData = audioPlayer.playingData.collectAsState().value
    val playIcon = painterResource(R.drawable.ic_play_circle_green)
    val playIconDisabled = painterResource(R.drawable.ic_play_circle_disabled)
    val pauseIcon = painterResource(R.drawable.ic_pause)

    IconButton(
        onClick = {
            if (playingData.isTourLocation(tour, location))
                audioPlayer.alternate()
            else
                if(currentPosition != null && location.isNear(currentPosition))
                    audioPlayer.playTourLocation(tour, location)
        }
    ) {
        Icon(
            painter = if (playingData.isPlayingTourLocation(tour, location))
                pauseIcon
            else if(currentPosition != null && location.isNear(currentPosition))
                playIcon
            else playIconDisabled,
            contentDescription = if (playingData.isPlayingTourLocation(tour, location))
                stringResource(id = R.string.pause_audio)
            else if(currentPosition != null && location.isNear(currentPosition))
                stringResource(id = R.string.play_audio)
            else stringResource(id = R.string.play_audio_disabled),
            tint = Color.Unspecified,
            modifier = Modifier.size(30.dp)
        )
    }
}


@Composable
fun Title(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

//********************** PREVIEWS **********************//
@Composable
@Preview
private fun LocationCardPreview() {
    LocationCard(MockToursAPI.sampleTour(), LocationAPIWithRetrofit.sampleLocation(), MapViewModel(LocalContext.current as Application), AudioPlayer())
}

@Composable
@Preview
fun LocationListPreview() {
    LocationList(MockToursAPI.sampleTour(), listOf(), MapViewModel(LocalContext.current as Application), AudioPlayer())
}
