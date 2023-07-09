package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPIWithRetrofit
import kotlinx.coroutines.Dispatchers

class LocationListViewModelFactory(private val tourId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationListViewModel::class.java)) {
            return LocationListViewModel(tourId = tourId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class LocationListViewModel(tourId: Int, private val locationApi: LocationAPI = LocationAPIWithRetrofit()) : ViewModel() {

    val locations = liveData(Dispatchers.IO) {
        emit(null)
        emit(locationApi.getTourLocations(tourId))
    }
}
