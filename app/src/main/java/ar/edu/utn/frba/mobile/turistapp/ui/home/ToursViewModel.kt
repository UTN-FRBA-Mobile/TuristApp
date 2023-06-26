package ar.edu.utn.frba.mobile.turistapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.ToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers

class ToursViewModel(private val toursAPI: ToursAPI = MockToursAPI()): ViewModel() {
    var nearbyTours = liveData(Dispatchers.IO) {
        emit(null)
        emit(toursAPI.getNearbyTours())
    }
    var favoriteTours = liveData(Dispatchers.IO) {
        emit(null)
        emit(FavoritesRepository().getFavorites())
    }
}
