package ar.edu.utn.frba.mobile.turistapp.core.models

data class MinifiedTour(
    val id: Int,
    val title: String,
    val description: String,
    val languages: Set<String>,
    val distance: Double,
    val image: String
)