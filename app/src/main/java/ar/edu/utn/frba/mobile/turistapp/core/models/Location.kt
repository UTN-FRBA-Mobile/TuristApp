package ar.edu.utn.frba.mobile.turistapp.core.models

import ar.edu.utn.frba.mobile.turistapp.core.utils.DistanceUtils
import com.google.android.gms.maps.model.LatLng

data class Location (
    val order: Int,
    val name: String,
    val description: String,
    val proximityLabel: String,
    var proximityValue: Int,
    val latitude: Double,
    val longitude: Double
) {
    fun isNear(actualPosition: LatLng): Boolean = distance(actualPosition) < 100

    fun distance(actualPosition: LatLng): Double {
        return DistanceUtils.calculateDistance(
            LatLng(latitude, longitude), actualPosition
        )
    }
}