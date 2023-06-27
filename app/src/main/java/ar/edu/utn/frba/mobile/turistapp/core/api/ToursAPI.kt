package ar.edu.utn.frba.mobile.turistapp.core.api

import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import ar.edu.utn.frba.mobile.turistapp.core.models.TourReview
import ar.edu.utn.frba.mobile.turistapp.core.utils.AvailableLanguages
import ar.edu.utn.frba.mobile.turistapp.core.utils.LocaleUtils

interface ToursAPI {
    suspend fun getNearbyTours(): List<MinifiedTour>
    suspend fun getTour(id: Int): TourResponse
}

class MockToursAPI: ToursAPI {
    override suspend fun getNearbyTours(): List<MinifiedTour> {
        val languaje = LocaleUtils.currentLocale()
        when(LocaleUtils.currentLocale()) {
            AvailableLanguages.English -> return TourRetriever.retrofit.retrieveHomeTours_en().tours
            AvailableLanguages.Spanish -> return TourRetriever.retrofit.retrieveHomeTours_es().tours
        }
    }

    override suspend fun getTour(id: Int): TourResponse {
        when(LocaleUtils.currentLocale()) {
            AvailableLanguages.English -> return TourRetriever.retrofit.retrieveTour_en()
            AvailableLanguages.Spanish -> return TourRetriever.retrofit.retrieveTour_es()
        }
    }

    companion object {
        fun sampleTours(): List<MinifiedTour> {
            return listOf(
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

        fun sampleTour(): TourResponse {
            return TourResponse(
                id = 1,
                title = "Buenos Aires City Center",
                description = "We will begin the tour at the National Congress and then walk along the renowned boulevard of Avenida de Mayo with its magnificent buildings.",
                rating = 4,
                ratingCount = 648,
                duration = 3.0,
                languages = setOf("English", "Spanish"),
                locations = setOf("Obelisco", "National Congress", "Casa Rosada", "Teatro Colón", "Café Tortoni"),
                image = "https://el-pais.brightspotcdn.com/dims4/default/bef130e/2147483647/strip/true/crop/930x639+0+22/resize/1440x990!/quality/90/?url=https%3A%2F%2Fel-pais-uruguay-production-web.s3.amazonaws.com%2Fbrightspot%2Fuploads%2F2022%2F08%2F12%2F62f6ac43c99e6.jpeg",
                reviews = setOf(
                    TourReview(stars = 4, text = "Lovely city <3"),
                    TourReview(stars = 5, text = "Great tour!"),
                    TourReview(stars = 5, text = "Excellent")
                )
            )
        }
    }
}