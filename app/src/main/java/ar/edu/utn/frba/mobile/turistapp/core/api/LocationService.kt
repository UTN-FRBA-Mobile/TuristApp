package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import retrofit2.http.GET

interface LocationService {

    @GET("fbffcce0-431c-40e3-985c-80367b488f1b")
    suspend fun retrieveTourLocations(): List<Location>

}