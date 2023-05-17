package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.HomeToursResponse
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import retrofit2.http.GET

interface TourService {
    @GET("1499a661-7d85-49a7-8342-ee65c2755733")
    suspend fun retrieveHomeTours(): HomeToursResponse

    @GET("3edf1c4c-a525-4bec-92e6-6bd665fb6401")
    suspend fun retrieveTour(id: Int): TourResponse
}