package ar.edu.utn.frba.mobile.turistapp.ui.map.locations_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.turistapp.R
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
    LazyColumn{
        item{
            Title(stringResource(R.string.locations))
            }
        items(locations) { location ->
            LocationCard(location)
        }
    }
}

@Composable
fun LocationCard(location: Location) {
    Column() {
        Row(modifier = Modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = location.order.toString(), style = MaterialTheme.typography.titleMedium)
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Chip(text = location.proximityLabel)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = location.proximityValue.toString() + "m", style = MaterialTheme.typography.bodyMedium)
        }
        Text(text = location.description,  style = MaterialTheme.typography.bodyMedium)
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
@Preview
private fun LocationCardPreview() {
    val testLocation: Location = Location(
        order = 1,
        name = "Casa Rosada",
        description = "La Casa Rosada es la sede del Poder Ejecutivo de la República Argentina. " +
                "Está ubicada en el barrio de Monserrat de la Ciudad Autónoma de Buenos Aires, frente" +
                " a la histórica Plaza de Mayo. Su denominación oficial es Casa de Gobierno.",
        proximityLabel = "Cerca",
        proximityValue = 10
    )
    LocationCard(testLocation)
}

@Composable
@Preview
private fun smallPlayerButton() {
    Icon(Icons.Rounded.PlayArrow, contentDescription = "Play")
}




