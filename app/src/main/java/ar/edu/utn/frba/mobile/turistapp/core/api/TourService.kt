package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.HomeToursResponse
import retrofit2.Call
import retrofit2.http.GET

interface TourService {
    @GET("89be4f51-860a-42eb-b5ec-46fff91896c2")
    suspend fun retrieveHomeTours(): HomeToursResponse
}