package ar.edu.utn.frba.mobile.turistapp.core.models

data class TourResponse(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Int,
    val ratingCount: Int,
    val duration: Double,
    val languages: Set<String>,
    val locations: Set<String>,
    val image: String,
    val reviews: Set<TourReview>
)