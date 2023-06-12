package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps

import android.location.Location
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.clusters.ZoneClusterItem

data class MapState(
    val lastKnownLocation: Location?,
    val clusterItems: List<ZoneClusterItem>,
)