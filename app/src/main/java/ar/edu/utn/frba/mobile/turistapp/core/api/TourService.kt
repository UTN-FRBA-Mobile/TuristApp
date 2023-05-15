package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.HomeToursResponse
import retrofit2.http.GET

interface TourService {
    @GET("1499a661-7d85-49a7-8342-ee65c2755733")
    suspend fun retrieveHomeTours(): HomeToursResponse
}