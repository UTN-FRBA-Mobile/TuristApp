package ar.edu.utn.frba.mobile.turistapp.core.models

data class HomeToursResponse(
    val tours_es: List<MinifiedTour>,
    val tours_en: List<MinifiedTour>
)