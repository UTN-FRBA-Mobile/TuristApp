package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.core.repository.AudioRepository
import ar.edu.utn.frba.mobile.turistapp.core.repository.LocationRepository


class LocationListViewModel(private val locationRepository: LocationRepository,
                            private val audioRepository: AudioRepository) : ViewModel() {

    val locations: LiveData<List<Location>> = locationRepository.loadData()
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
