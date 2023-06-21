package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.calculateCameraViewPoints
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.getCenterOfMarkers
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {

    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            currentLocation = null,
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

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                // Update state with new location
                val newLatLng = LatLng(location.latitude, location.longitude)
                state.value.currentLocation = newLatLng
            }
        }
    }

    private val locationRequest: LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
        setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        setWaitForAccurateLocation(true)
    }.build()

    fun startLocationUpdates(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } catch (e: SecurityException) {
            // handle the security exception
        }
    }
/*
* Recibe como parámetro la lista de coordenadas de las locations de una ruta y devuelve un LatLngBounds que contiene todas las coordenadas.
* Se utiliza para centrar la pantalla en los puntos de interés de una ruta.
* */
    fun calculateZoneLatLngBounds(locationCoordinates: List<LatLng>): LatLngBounds {
        // Get all the points from all the polygons and calculate the camera view that will show them all.
        return locationCoordinates.calculateCameraViewPoints().getCenterOfMarkers()
    }

}