package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.calculateCameraViewPoints
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.getCenterOfMarkers
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class MapViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val currentLocation: MutableStateFlow<LatLng?> = MutableStateFlow(null)

    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    @SuppressLint("MissingPermission")
    fun getDeviceLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null)
                    currentLocation.value = LatLng(task.result.latitude, task.result.longitude)
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }


    private val locationListener = LocationListener {
            location -> currentLocation.value = LatLng(location.latitude, location.longitude)
    }

    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    fun startLocationUpdates() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication() as Context)
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationListener,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // handle the security exception
        }
    }

    fun stopLocationUpdates() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication() as Context)
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationListener)
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