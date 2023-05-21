package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.HomeToursResponse
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import retrofit2.http.GET

interface TourService {
    @GET("1499a661-7d85-49a7-8342-ee65c2755733")
    suspend fun retrieveHomeTours(): HomeToursResponse

    @GET("b27e0ca7-ba49-47dc-92e9-4dbfb4be6db1")
    suspend fun retrieveTour(): TourResponse
}