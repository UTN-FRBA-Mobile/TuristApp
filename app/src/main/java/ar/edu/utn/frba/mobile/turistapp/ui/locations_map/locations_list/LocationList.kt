package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPIWithRetrofit
import ar.edu.utn.frba.mobile.turistapp.core.models.Location


@Composable
fun LocationListScreen(locations: List<Location>) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        LocationList(locations)
    }
}

@Composable
fun LocationList(locations: List<Location>) {
    LazyColumn(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .padding(8.dp)){
        items(locations) { location ->
            LocationCard(location)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun LocationCard(location: Location) {
    val isExpanded = remember { mutableStateOf(false) }
    // Fetching the local context
    val mContext = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .clickable { isExpanded.value = !isExpanded.value } // toggles the expanded state on click
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Row(modifier = Modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = location.order.toString(), style = MaterialTheme.typography.titleMedium)
                Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                Chip(text = location.proximityLabel)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = location.proximityValue.toString() + "m", style = MaterialTheme.typography.bodyMedium)
            }
            // If the card is expanded, show the description
            if (isExpanded.value) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = location.description,  style = MaterialTheme.typography.bodyMedium)
            }
        }
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

@Composable
fun SmallPlayerButton(viewModel: LocationListViewModel, audioFileName: String) {
    val pauseIcon = painterResource(R.drawable.ic_pause)
    val playCircleIcon = painterResource(R.drawable.ic_play_circle)

    IconButton(onClick = { viewModel.togglePlay(audioFileName) }) {
        val actualIcon = if (viewModel.isPlaying.value) pauseIcon else playCircleIcon
        Icon(painter = actualIcon, contentDescription = stringResource(id = R.string.play_audio))
    }
}


//********************** PREVIEWS **********************//
@Composable
@Preview
private fun LocationCardPreview() {

    LocationCard(LocationAPIWithRetrofit.sampleLocation())
}

@Composable
@Preview
fun LocationListPreview() {
    LocationList(LocationAPIWithRetrofit.sampleLocations())
}




