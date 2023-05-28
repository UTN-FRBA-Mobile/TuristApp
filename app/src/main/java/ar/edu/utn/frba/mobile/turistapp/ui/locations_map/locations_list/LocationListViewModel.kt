package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.CreationExtras
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPI
import ar.edu.utn.frba.mobile.turistapp.core.api.LocationAPIWithRetrofit
import ar.edu.utn.frba.mobile.turistapp.core.repository.AudioRepository
import kotlinx.coroutines.Dispatchers

class LocationListViewModelFactory(private val tourId: Int, private val context: Context) : ViewModelProvider.Factory {
    private val audioRepository: AudioRepository = AudioRepository(context)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationListViewModel::class.java)) {
            return LocationListViewModel(tourId = tourId, audioRepository = audioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class LocationListViewModel(tourId: Int, private val locationApi: LocationAPI = LocationAPIWithRetrofit(),
                            private val audioRepository: AudioRepository
) : ViewModel() {

    val locations = liveData(Dispatchers.IO) {
        emit(null)
        emit(locationApi.getTourLocations(tourId))
    }

    // State for whether the media is currently playing
    val isPlaying = mutableStateOf(false)

    fun togglePlay(audioFileName: String) {
        val audioResourceId = audioRepository.getAudioResourceId(audioFileName)

        if (!isPlaying.value) {
            if (!audioRepository.isPlaying()) {
                audioRepository.playAudio(audioResourceId)
            }
        } else {
            audioRepository.pauseAudio()
        }

        isPlaying.value = !isPlaying.value
    }

    override fun onCleared() {
        super.onCleared()
        audioRepository.releasePlayer()
    }
}
