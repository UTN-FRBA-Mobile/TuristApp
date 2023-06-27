package ar.edu.utn.frba.mobile.turistapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.ToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.core.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers

interface FavoritesObserver {
    fun didUpdateFavorites()
}
class ToursViewModel(private val toursAPI: ToursAPI = MockToursAPI()): ViewModel(), FavoritesObserver {
    var nearbyTours = liveData(Dispatchers.IO) {
        emit(null)
        emit(toursAPI.getNearbyTours())
    }

    var mutableFavoriteTours: MutableLiveData<List<MinifiedTour>> = MutableLiveData()
    var favoriteTours = liveData(Dispatchers.IO) {
        emit(null)
        emit(FavoritesRepository().getFavorites())
    }
    init {
        FavoritesRepository.observers.add(this)
    }

    override fun didUpdateFavorites() {
        mutableFavoriteTours.postValue(FavoritesRepository().getFavorites())
        favoriteTours = mutableFavoriteTours
    }
}
