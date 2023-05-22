package ar.edu.utn.frba.mobile.turistapp.ui.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.ToursAPI
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
}
