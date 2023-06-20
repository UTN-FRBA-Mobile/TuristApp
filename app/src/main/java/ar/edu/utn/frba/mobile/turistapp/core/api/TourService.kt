package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.HomeToursResponse
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import retrofit2.http.GET

interface TourService {
    @GET("88dbead1-d07c-4c16-9f50-e20842e6c2a2")
    suspend fun retrieveHomeTours_es(): HomeToursResponse

    @GET("c47b4969-cb11-4f39-8d82-e63382e96814")
    suspend fun retrieveHomeTours_en(): HomeToursResponse

    @GET("70f51d5c-a207-416e-bd9c-81089aeb1551")
    suspend fun retrieveTour_es(): TourResponse

    @GET("7637ec50-234d-4813-ad77-b81bc93ab2a8")
    suspend fun retrieveTour_en(): TourResponse
}