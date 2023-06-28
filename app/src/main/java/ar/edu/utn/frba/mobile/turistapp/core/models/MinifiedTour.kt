package ar.edu.utn.frba.mobile.turistapp.core.models

data class MinifiedTour(
    val id: Int,
    val title: String,
    val description: Set<List<String>>,
    val languages: Set<String>,
    val distance: Double,
    val image: String
)