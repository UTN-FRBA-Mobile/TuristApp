package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour

interface ToursAPI {
    suspend fun getHomeTours(): List<MinifiedTour>
}

class MockToursAPI: ToursAPI {
    override suspend fun getHomeTours(): List<MinifiedTour> {
        return TourRetriever.retrofit.retrieveHomeTours().tours
    }

    companion object {
        fun sampleTours(): List<MinifiedTour> {
            return listOf<MinifiedTour>(
                MinifiedTour(
                    1,
                    "Buenos Aires City Center",
                    "Obelisco, Puerto Madero, La Boca",
                    setOf("English", "Spanish"),
                    2.0,
                    "http://image.url/test.jpg"
                ),
                MinifiedTour(
                    2,
                    "Buenos Aires City Center 2",
                    "Casa Rosada, Obelisco, Teatro Colón",
                    setOf("Spanish"),
                    1.2,
                    "http://image.url/test.jpg"
                )
            )
        }
    }
}