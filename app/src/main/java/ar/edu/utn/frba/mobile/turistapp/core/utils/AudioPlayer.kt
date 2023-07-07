package ar.edu.utn.frba.mobile.turistapp.core.utils

import android.media.MediaPlayer
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse
import kotlinx.coroutines.flow.MutableStateFlow

data class PlayingData(val tour: Int?, val location: Int?, val playing: Boolean) {
    fun isTourLocation(tour: TourResponse, location: Location): Boolean {
        return this.tour == tour.id && this.location == location.order
    }

    fun isPlayingTourLocation(tour: TourResponse, location: Location): Boolean {
        return this.isTourLocation(tour, location) && this.playing
    }

    fun isActive(): Boolean {
        return this.tour != null && this.location != null
    }
}

val NotPlayingData = PlayingData(null, null, false)

class AudioPlayer {
    private val mediaPlayer = MediaPlayer()
    private val baseURL = "https://github.com/UTN-FRBA-Mobile/TuristApp/raw/main/audios/"
    val playingData: MutableStateFlow<PlayingData> = MutableStateFlow(NotPlayingData)

    init {
        mediaPlayer.setOnCompletionListener {
            this.playingData.value = NotPlayingData
        }
    }

    fun playTourLocation(tour: TourResponse, location: Location) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this.baseURL + tour.id + "_" + location.order + "_" + LocaleUtils.currentLocaleCode() + ".mp3")
        mediaPlayer.prepare()
        mediaPlayer.start()
        this.playingData.value = PlayingData(tour.id, location.order, true)
    }

    private fun pause() {
        mediaPlayer.pause()
        this.playingData.value = this.playingData.value.copy(playing = false)
    }

    private fun resume() {
        mediaPlayer.start()
        mediaPlayer.currentPosition
        this.playingData.value = this.playingData.value.copy(playing = true)
    }

    fun alternate() {
        if (this.playingData.value.playing)
            this.pause()
        else
            this.resume()
    }

    fun currentPosition(): Float = mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration
}