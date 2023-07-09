package ar.edu.utn.frba.mobile.turistapp.ui.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.ToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.core.repository.FavoritesRepository
import ar.edu.utn.frba.mobile.turistapp.core.utils.LocaleUtils
import kotlinx.coroutines.Dispatchers

class TourViewModelFactory(private val tourId: Int): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TourViewModel::class.java)) {
            return TourViewModel(tourId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TourViewModel(private val tourId: Int, private val toursAPI: ToursAPI = MockToursAPI()): ViewModel() {
    var tour = liveData(Dispatchers.IO) {
        emit(null)
        emit(toursAPI.getTour(tourId))
    }

    fun isFavorite(): Boolean {
        return FavoritesRepository().isFavorite(tourId)
    }

    fun didTapHeart() {
        tour.value?.let {
            val minifiedTour = MinifiedTour(
                id = it.id,
                title = it.title,
                description = it.description,
                languages = it.languages,
                distance = 0.0,
                image = it.image
            )
            FavoritesRepository().toggleFavorite(tour = minifiedTour)
        }
    }
}
