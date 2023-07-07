package ar.edu.utn.frba.mobile.turistapp.ui.audio_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlin.time.Duration.Companion.seconds

@Composable
fun AudioBar(audioPlayer: AudioPlayer) {
    val playingData = audioPlayer.playingData.collectAsState().value
    var currentPosition = remember { mutableStateOf(0F) }.value

    if (playingData.playing) {
        LaunchedEffect(Unit) {
            while(true) {
                currentPosition = audioPlayer.currentPosition()
                delay(1.seconds / 30)
            }
        }
    } else {
        currentPosition = audioPlayer.currentPosition()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Black)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .align(Alignment.CenterVertically)
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .align(Alignment.CenterVertically)
                .background(Color.Green)
                .padding(end = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .fillMaxWidth(currentPosition)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomAudioPlayerRowPreview() {
    AudioBar(AudioPlayer())
}
