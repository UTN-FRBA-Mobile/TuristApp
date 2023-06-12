package ar.edu.utn.frba.mobile.turistapp.ui.googleMaps

import android.location.Location
import ar.edu.utn.frba.mobile.turistapp.ui.googleMaps.clusters.ZoneClusterItem

data class MapState(
    val lastKnownLocation: Location?,
    val clusterItems: List<ZoneClusterItem>,
)