package ar.edu.utn.frba.mobile.turistapp.core.models

data class Location (
    val order: Int,
    val name: String,
    val description: String,
    val proximityLabel: String,
    val proximityValue: Int,
    val latitude: Double,
    val longitude: Double
    )