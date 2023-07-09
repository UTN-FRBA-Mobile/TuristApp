package ar.edu.utn.frba.mobile.turistapp.core.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object DistanceUtils {
    fun calculateDistance(locationA: LatLng, locationB: LatLng): Double {
        val earthRadiusInKms = 6371
        val dLat = Math.toRadians(locationB.latitude - locationA.latitude)
        val dLon = Math.toRadians(locationB.longitude - locationA.longitude)
        val a =
            sin(dLat / 2).pow(2) + cos(Math.toRadians(locationA.latitude)) * cos(
                Math.toRadians(
                    locationB.latitude
                )
            ) * sin(
                dLon / 2
            ).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distanceInKms = earthRadiusInKms * c
        return distanceInKms * 1000
    }
}