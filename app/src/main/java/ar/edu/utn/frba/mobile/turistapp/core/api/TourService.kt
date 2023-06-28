package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.HomeToursResponse
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TourService {
    @GET("tours/home.json")
    suspend fun retrieveHomeTours(): HomeToursResponse

    @GET("tour/{id}/{lang}.json")
    suspend fun retrieveTour(@Path("id") id: String, @Path("lang") lang: String): TourResponse
}