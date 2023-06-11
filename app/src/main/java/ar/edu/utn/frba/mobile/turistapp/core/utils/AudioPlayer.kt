package ar.edu.utn.frba.mobile.turistapp.core.utils

import android.media.MediaPlayer
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AudioPlayer {
    private val mediaPlayer = MediaPlayer()
    private val baseURL = "https://github.com/UTN-FRBA-Mobile/TuristApp/raw/main/audios/"

    // Expose isPlaying as a StateFlow.
    // Esto es para observar el estado del reproductor en la pantalla y que el ícono cambie de play a pause
    // Se necesita un AudioPlayer por botón porque sino comparten el estado y cambian a play o pause todos al mismo tiempo
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    init {
        mediaPlayer.setOnCompletionListener {
            _isPlaying.value = false
        }
    }

    fun play(tour: TourResponse, location: Location) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this.baseURL + tour.id + "_" + location.order + "_" + LocaleUtils.currentLocaleCode() + ".mp3")
        mediaPlayer.prepare()
        mediaPlayer.start()
        _isPlaying.value = true
    }

    fun pause() {
        mediaPlayer.pause()
        _isPlaying.value = false
    }
}