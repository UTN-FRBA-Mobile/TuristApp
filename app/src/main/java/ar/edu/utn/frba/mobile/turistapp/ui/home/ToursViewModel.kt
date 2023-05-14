package ar.edu.utn.frba.mobile.turistapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.ToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.TourRetriever
import kotlinx.coroutines.Dispatchers

class ToursViewModel(private val toursAPI: ToursAPI = MockToursAPI()): ViewModel() {
    var tours = liveData(Dispatchers.IO) {
        emit(null)
        emit(toursAPI.getHomeTours())
    }
}
