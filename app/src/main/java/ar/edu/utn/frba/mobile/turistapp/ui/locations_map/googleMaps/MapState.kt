package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.location.Location
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val lastKnownLocation: Location?,
    var currentLocation: LatLng?,
)