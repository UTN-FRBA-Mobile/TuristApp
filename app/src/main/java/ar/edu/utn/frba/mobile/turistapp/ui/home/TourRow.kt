package ar.edu.utn.frba.mobile.turistapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.turistapp.core.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI

@Composable
fun TourRow(tour: MinifiedTour) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp).border(1.dp, Color.Gray)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                Modifier.size(size = 80.dp)
                        .background(color = Color.Gray)
            )
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
                Text(text = tour.description)
                Text(text = tour.distance.toString() + " km")
                Text(text = tour.languages.joinToString(" | "))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TourRowPreview() {
    TourRow(MockToursAPI.sampleTours().first())
}