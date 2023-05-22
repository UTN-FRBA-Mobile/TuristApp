package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
public fun MapScreen() {
    //TODO: Agregar mapa
    Box(Modifier.fillMaxSize().background(Color.DarkGray)) {
        Text("Próximamente un mapa...", Modifier.background(Color.White))
    }
}