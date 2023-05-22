package ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationListViewModel(private val context: Context) : ViewModel() {
    private val mediaPlayer = MediaPlayer()

    // State for whether the media is currently playing
    val isPlaying = mutableStateOf(false)

    fun togglePlay(audioFileName: String) {
        val audioFileId: Int = context.resources.getIdentifier(audioFileName, "raw", context.packageName)

        if (!isPlaying.value) {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.reset()
                val afd = context.resources.openRawResourceFd(audioFileId)
                mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                mediaPlayer.prepare()
            }
            mediaPlayer.start()
        } else {
            mediaPlayer.pause()
        }

        isPlaying.value = !isPlaying.value
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}
