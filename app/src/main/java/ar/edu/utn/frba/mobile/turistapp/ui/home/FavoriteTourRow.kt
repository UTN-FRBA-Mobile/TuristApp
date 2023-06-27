package ar.edu.utn.frba.mobile.turistapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import coil.compose.AsyncImage
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI

@Preview(showBackground = true)
@Composable
fun FavoriteTourRowPreview() {
    FavoriteTourRow(MockToursAPI.sampleTours().first())
}

@Composable
fun FavoriteTourRow(tour: MinifiedTour, navController: NavController? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
            .border(1.dp, Color.Gray)
            .clickable {
                navController?.navigate("tour/${tour.id}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                Modifier.size(size = 90.dp)
                    .background(color = Color.Gray)
            ) {
                AsyncImage(
                    model = tour.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                Modifier
                    .weight(2F)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = tour.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(text = tour.languages.joinToString(" | "))
            }
        }
    }
}