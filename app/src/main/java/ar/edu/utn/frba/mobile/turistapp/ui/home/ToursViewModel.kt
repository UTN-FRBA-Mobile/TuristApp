package ar.edu.utn.frba.mobile.turistapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.ToursAPI
import kotlinx.coroutines.Dispatchers

class ToursViewModel(private val toursAPI: ToursAPI = MockToursAPI()): ViewModel() {
    var tours = liveData(Dispatchers.IO) {
        emit(null)
        emit(toursAPI.getMinifiedTours())
    }
}
