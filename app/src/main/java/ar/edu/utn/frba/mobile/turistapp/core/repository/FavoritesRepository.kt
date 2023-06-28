package ar.edu.utn.frba.mobile.turistapp.core.repository

import android.content.SharedPreferences
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.ui.home.FavoritesObserver
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.Exception

class FavoritesRepository {
    companion object {
        @JvmStatic
        var sharedPreference: SharedPreferences? = null

        @JvmStatic
        var observers: MutableList<FavoritesObserver> = arrayListOf()
    }

    val favoritesKey = "favorites"

    private fun persistFavorites(favorites: List<MinifiedTour>) {
        val editor = sharedPreference?.edit()
        val json = Gson().toJson(favorites)
        editor?.putString(favoritesKey, json)
        editor?.apply()
    }

    fun recordFavorite(tour: MinifiedTour) {
        if (sharedPreference == null) { return }
        val favoritesList = getFavorites().filter { it.id != tour.id }.toMutableList()
        favoritesList.add(tour)
        persistFavorites(favoritesList)
    }

    fun removeFavorite(tour: MinifiedTour) {
        if (sharedPreference == null) { return }
        val favoritesList = getFavorites().filter { it.id != tour.id }.toMutableList()
        persistFavorites(favoritesList)
    }

    fun getFavorites(): List<MinifiedTour> {
        try {
            if (sharedPreference != null) {
                val json: String? = sharedPreference?.getString(favoritesKey, "")
                if (json != null && json.isNotEmpty()) {
                    val obj: List<MinifiedTour> =
                        GsonBuilder().create().fromJson(json, Array<MinifiedTour>::class.java).toList()
                    return obj
                }
            }
        }
        catch (e: Exception) {
            val favoritesList = emptyList<MinifiedTour>()
            persistFavorites(favoritesList)
            return favoritesList
        }
        return emptyList()
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