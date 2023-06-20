package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.location.Location
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.ZoneClusterItem
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val lastKnownLocation: Location?,
    var currentLocation: LatLng?,
    val clusterItems: List<ZoneClusterItem>,
)