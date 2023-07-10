package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {
    @GET("locations/{id}/{lang}.json")
    suspend fun retrieveTourLocation(@Path("id") id: String, @Path("lang") lang: String): List<Location>
}