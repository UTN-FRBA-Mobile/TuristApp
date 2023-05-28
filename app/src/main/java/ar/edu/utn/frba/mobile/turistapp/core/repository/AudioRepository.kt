package ar.edu.utn.frba.mobile.turistapp.core.repository

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer

class AudioRepository(private val context: Context) {
    //TODO: Falta probarlo
    private val mediaPlayer = MediaPlayer()

    // Returns resource id for a raw resource
    fun getAudioResourceId(audioFileName: String): Int =
        context.resources.getIdentifier(audioFileName, "raw", context.packageName)

    // Opens raw resource file descriptor
    fun openRawResourceFd(resourceId: Int): AssetFileDescriptor =
        context.resources.openRawResourceFd(resourceId)

    fun isPlaying(): Boolean = mediaPlayer.isPlaying

    fun playAudio(resourceId: Int) {
        mediaPlayer.reset()
        val afd = openRawResourceFd(resourceId)
        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun pauseAudio() {
        mediaPlayer.pause()
    }

    fun releasePlayer() {
        mediaPlayer.release()
    }
}