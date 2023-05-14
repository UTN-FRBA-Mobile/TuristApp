package ar.edu.utn.frba.mobile.turistapp.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ToursAPI {
    suspend fun getMinifiedTours(): Array<MinifiedTour>
}

class MockToursAPI: ToursAPI {
    override suspend fun getMinifiedTours(): Array<MinifiedTour> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        return sampleTours()
    }

    companion object {
        fun sampleTours(): Array<MinifiedTour> {
            return arrayOf(
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