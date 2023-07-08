package ar.edu.utn.frba.mobile.turistapp.ui.audio_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.utils.AudioPlayer
import kotlinx.coroutines.delay

@Composable
fun AudioBar(audioPlayer: AudioPlayer) {
    val playingData = audioPlayer.playingData.collectAsState().value
    var currentPosition by remember { mutableStateOf(0F) }

    LaunchedEffect(playingData.playing) {
        while(playingData.playing) {
            currentPosition = audioPlayer.currentPosition()
            delay(10)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(5.dp)
                .align(Alignment.CenterVertically)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxHeight()
                    .fillMaxWidth(currentPosition)
            )
        }

        Button(
            onClick = { audioPlayer.alternate() },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            val playIcon = painterResource(R.drawable.ic_play_circle_green)
            val pauseIcon = painterResource(R.drawable.ic_pause)
            Icon(painter = if (playingData.playing) pauseIcon else playIcon,
                contentDescription = stringResource(id = if (playingData.playing) R.string.pause_audio else R.string.play_audio),
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomAudioPlayerRowPreview() {
    AudioBar(AudioPlayer())
}
