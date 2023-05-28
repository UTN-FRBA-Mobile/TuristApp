package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.Location


interface LocationAPI {
    suspend fun getTourLocations(id: Int): List<Location>
}


class LocationAPIWithRetrofit : LocationAPI {
    override suspend fun getTourLocations(id: Int): List<Location> {
        return LocationRetriever.retrofit.retrieveTourLocations()
}

    companion object {
        private val testLocation: Location = Location(
            order = 1,
            name = "Casa Rosada",
            description = "La Casa Rosada es la sede del Poder Ejecutivo de la República Argentina. " +
                    "Está ubicada en el barrio de Monserrat de la Ciudad Autónoma de Buenos Aires, frente" +
                    " a la histórica Plaza de Mayo. Su denominación oficial es Casa de Gobierno.",
            proximityLabel = "Cerca",
            proximityValue = 10,
            latitude = -34.60800822829423,
            longitude = -58.37026107360327,
            audioFileName = "audio_test"
        )

        fun sampleLocations(): List<Location> {
            return listOf(testLocation, testLocation, testLocation, testLocation, testLocation)
        }

        fun sampleLocation(): Location {
            return testLocation
        }
    }

}