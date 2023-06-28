package ar.edu.utn.frba.mobile.turistapp.core.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TourRetriever {
    private const val BASE_URL = "https://raw.githubusercontent.com/UTN-FRBA-Mobile/TuristApp/main/api/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TourService::class.java)
    }
}