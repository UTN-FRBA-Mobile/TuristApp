package ar.edu.utn.frba.mobile.turistapp.core.utils

import android.media.MediaPlayer
import ar.edu.utn.frba.mobile.turistapp.core.models.Location
import ar.edu.utn.frba.mobile.turistapp.core.models.TourResponse

class AudioPlayer {
    private val mediaPlayer = MediaPlayer()
    private val baseURL = "https://github.com/UTN-FRBA-Mobile/TuristApp/raw/main/audios/"

    fun isPlaying(): Boolean = mediaPlayer.isPlaying

    fun play(tour: TourResponse, location: Location) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this.baseURL + tour.id + "_" + location.order + "_" + LocaleUtils.currentLocaleCode() + ".mp3")
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }
}