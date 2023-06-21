import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.ui.IconGenerator
import com.google.maps.android.ui.SquareTextView
import kotlinx.coroutines.launch


@Composable
fun GoogleMapScreen(mapViewModel: MapViewModel, locations: List<Location>) {
    // locations.map { it -> LatLng(it.latitude, it.longitude) }
    val state = mapViewModel.state.value //TODO: Hacer que el state sea observable
    // Set properties using MapProperties which you can use to recompose the map
    val mapProperties = MapProperties(
        // Only enable if user has accepted location permissions.
        isMyLocationEnabled = state.lastKnownLocation != null,
    )
    val cameraPositionState = rememberCameraPositionState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState
        ) {
            val scope = rememberCoroutineScope()
            for (location in locations) {
                Marker(
                    state = rememberMarkerState(position = LatLng(location.latitude, location.longitude)),
                    title = location.name,
                    icon = generateMarkerIcon(location.order.toString())
                )
            }
            MapEffect() { map ->
                    map.setOnMapLoadedCallback {
                            scope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLngBounds(
                                        //Centrar la pantalla del mapa en las locations
                                        mapViewModel.calculateZoneLatLngBounds(locations.map{LatLng(it.latitude, it.longitude)}),
                                        0
                                    ),
                                )
                    }
                }
            }
        }
    }
}

@Composable
fun generateMarkerIcon(text: String): BitmapDescriptor {
    val context = LocalContext.current

    // Use IconGenerator from Google Maps Android API Utils
    val iconGenerator = IconGenerator(context)
    iconGenerator.setContentPadding(0, 0, 0, 0)

    // Create a custom view with the desired number
    val squareTextView = SquareTextView(context)
    squareTextView.text = text

    // Set the custom view as the content of the IconGenerator
    iconGenerator.setContentView(squareTextView)

    // Generate a bitmap descriptor from the IconGenerator
    val bitmap = iconGenerator.makeIcon()
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}