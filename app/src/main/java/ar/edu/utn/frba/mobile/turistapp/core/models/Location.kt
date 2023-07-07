package ar.edu.utn.frba.mobile.turistapp.core.models

data class Location (
    val order: Int,
    val name: String,
    val description: String,
    val proximityLabel: String,
    var proximityValue: Int,
    val latitude: Double,
    val longitude: Double
) {
    fun isNear(): Boolean = proximityValue < 100
}