package ar.edu.utn.frba.mobile.turistapp.core

data class Tour(
    val title: String,
    val locationsDescription: String,
    val description: String,
    val languages: Set<String>,
    val latitude: Double,
    val longitude: Double
)