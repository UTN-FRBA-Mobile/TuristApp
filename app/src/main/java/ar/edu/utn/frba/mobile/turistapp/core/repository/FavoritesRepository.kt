package ar.edu.utn.frba.mobile.turistapp.core.repository

import android.content.SharedPreferences
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.ui.home.FavoritesObserver
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class FavoritesRepository {
    companion object {
        @JvmStatic
        var sharedPreference: SharedPreferences? = null

        @JvmStatic
        var observers: MutableList<FavoritesObserver> = arrayListOf()
    }

    val favoritesKey = "favorites"

    fun recordFavorite(tour: MinifiedTour) {
        if (sharedPreference == null) { return }

        var favoritesList = getFavorites().filter { it.id != tour.id }.toMutableList()
        favoritesList.add(tour)
        var editor = sharedPreference?.edit()
        val gson = Gson()
        val json = gson.toJson(favoritesList.toList())
        editor?.putString(favoritesKey, json)
        editor?.commit()
    }

    fun removeFavorite(tour: MinifiedTour) {
        if (sharedPreference == null) { return }

        var favoritesList = getFavorites().filter { it.id != tour.id }.toMutableList()
        var editor = sharedPreference?.edit()
        val gson = Gson()
        val json = gson.toJson(favoritesList.toList())
        editor?.putString(favoritesKey, json)
        editor?.commit()
    }

    fun getFavorites(): List<MinifiedTour> {
        if (sharedPreference != null) {
            var json: String? = sharedPreference?.getString(favoritesKey, "")
            if (json != null && json.isNotEmpty()) {
                val obj: List<MinifiedTour> =
                    GsonBuilder().create().fromJson(json, Array<MinifiedTour>::class.java).toList()
                return obj
            }
        }
        return emptyList<MinifiedTour>()
    }

    fun isFavorite(id: Int): Boolean {
        return getFavorites().filter{ it.id == id }.isNotEmpty()
    }

    fun toggleFavorite(tour: MinifiedTour) {
        if (isFavorite(tour.id)) {
            removeFavorite(tour)
        } else {
            recordFavorite(tour)
        }
        observers.forEach { it.didUpdateFavorites() }
    }
}