package ar.edu.utn.frba.mobile.turistapp.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ToursAPI {
    suspend fun getTours(): Array<Tour>
}

class MockToursAPI: ToursAPI {
    override suspend fun getTours(): Array<Tour> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        return sampleTours()
    }

    companion object {
        fun sampleTours(): Array<Tour> {
            return arrayOf(
                Tour(
                    "Buenos Aires City Center",
                    "Obelisco, Puerto Madero, La Boca",
                    "We will begin the tour at the National Congress and then walk along the renowned boulevard of Avenida de Mayo with its magnificent buildings.",
                    setOf("English", "Spanish"),
                    0.0,
                    0.0
                ),
                Tour(
                    "Buenos Aires City Center 2",
                    "Casa Rosada, Obelisco, Teatro Colón",
                    "We will begin the tour at the National Congress and then walk along the renowned boulevard of Avenida de Mayo with its magnificent buildings.",
                    setOf("Spanish"),
                    0.0,
                    0.0
                )
            )
        }
    }
}