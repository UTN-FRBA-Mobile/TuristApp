package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import retrofit2.http.GET

interface LocationService {

    @GET("81e2a27f-d7e3-4355-a3b8-e31361564e0e")
    suspend fun retrieveTourLocations_es(): List<Location>

    @GET("f7cb26bd-f7f6-4858-8322-556932255dce")
    suspend fun retrieveTourLocations_en(): List<Location>

}