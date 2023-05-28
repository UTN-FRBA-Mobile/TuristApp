package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap

@Composable
fun GoogleMapsScreen(){
    GoogleMap(modifier= Modifier.fillMaxSize())
}