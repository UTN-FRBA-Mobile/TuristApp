package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.calculateCameraViewPoints
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.getCenterOfMarkers
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            currentLocation = null,
        )
    )
    val distances = mutableListOf<Int>()
    val locationsList = mutableStateListOf<Location>()

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

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            distances.clear()
            for (location in locationResult.locations) {
                // Update state with new location
                val newLatLng = LatLng(location.latitude, location.longitude)
                state.value.currentLocation = newLatLng

                for (index in 0 until locationsList.count()) {
                    val distance = newLatLng.let { actualLocation ->
                        calculateDistance(
                            actualLocation,
                            locationsList[index].latitude,
                            locationsList[index].longitude
                        )
                    }
                    locationsList[index] = locationsList[index].copy(proximityValue = distance.toInt())
                    distances.add(distance.toInt())
                }

                val distancesAsString = distances.joinToString()
                Log.i("Distancias", distancesAsString)
                Log.i("ubicacion: ", newLatLng.toString())
            }
        }
    }


    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    fun startLocationUpdates(context: Context) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // handle the security exception
        }
    }

    fun stopLocationUpdates(context: Context) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
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

    fun calculateDistance(location1: LatLng, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371 // Radio de la Tierra en kilómetros
        val dLat = Math.toRadians(lat2 - location1.latitude)
        val dLon = Math.toRadians(lon2 - location1.longitude)
        val a =
            sin(dLat / 2).pow(2) + cos(Math.toRadians(location1.latitude)) * cos(Math.toRadians(lat2)) * sin(
                dLon / 2
            ).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = earthRadius * c
        return distance * 1000 // Convertir la distancia a metros
    }

}