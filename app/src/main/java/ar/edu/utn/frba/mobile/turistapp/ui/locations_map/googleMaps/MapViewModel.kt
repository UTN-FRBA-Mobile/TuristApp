package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.ZoneClusterItem
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.ZoneClusterManager
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.calculateCameraViewPoints
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.getCenterOfPolygon
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.model.polygonOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {

    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            clusterItems = listOf(
                ZoneClusterItem(
                    id = "zone-1",
                    title = "Obelisco de Buenos Aires",
                    snippet = "Este es el Obelisco de Buenos Aires",
                    polygonOptions = polygonOptions {
                        add(LatLng(-34.602528, -58.382261))
                        add(LatLng(-34.602580, -58.381061))
                        add(LatLng(-34.604547, -58.380942))
                        add(LatLng(-34.604599, -58.382169))
                        fillColor(POLYGON_FILL_COLOR)
                    }
                ),
                ZoneClusterItem(
                    id = "zone-2",
                    title = "Teatro Colón",
                    snippet = "Este es el Teatro Colón",
                    polygonOptions = polygonOptions {
                        add(LatLng(-34.600476, -58.385332))
                        add(LatLng(-34.601538, -58.385233))
                        add(LatLng(-34.601386, -58.382369))
                        add(LatLng(-34.600312, -58.382437))
                        fillColor(POLYGON_FILL_COLOR)
                    }
                ),
                ZoneClusterItem(
                    id = "zone-3",
                    title = "Congreso de la Nación Argentina",
                    snippet = "Este es el Congreso de la Nación Argentina",
                    polygonOptions = polygonOptions {
                        add(LatLng(-34.609271, -58.393395))
                        add(LatLng(-34.609306, -58.391431))
                        add(LatLng(-34.610208, -58.391300))
                        add(LatLng(-34.610423, -58.393330))
                        fillColor(POLYGON_FILL_COLOR)
                    }
                )
            )
        )
    )

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.value = state.value.copy(
                        lastKnownLocation = task.result,
                    )
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    fun setupClusterManager(
        context: Context,
        map: GoogleMap,
    ): ZoneClusterManager {
        val clusterManager = ZoneClusterManager(context, map)
        clusterManager.addItems(state.value.clusterItems)
        return clusterManager
    }

    fun calculateZoneLatLngBounds(): LatLngBounds {
        // Get all the points from all the polygons and calculate the camera view that will show them all.
        val latLngs = state.value.clusterItems.map { it.polygonOptions }
            .map { it.points.map { LatLng(it.latitude, it.longitude) } }.flatten()
        return latLngs.calculateCameraViewPoints().getCenterOfPolygon()
    }



    companion object {
        private val POLYGON_FILL_COLOR = Color.parseColor("#ABF44336")
    }
}