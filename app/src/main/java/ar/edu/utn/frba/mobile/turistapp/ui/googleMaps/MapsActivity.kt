package ar.edu.utn.frba.mobile.turistapp.ui.googleMaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.compose.GoogleMap

class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleMap()
        }
    }
}

@Composable
fun MyGoogleMaps(){
    GoogleMap(modifier= Modifier.fillMaxSize())
}