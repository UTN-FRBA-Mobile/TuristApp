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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun AudioBar(progress: Float, isPlaying: Boolean, onPlayStopClick: () -> Unit,  locations: @Composable () -> Unit) {
    val playbackProgress = remember { mutableStateOf(0.0f) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Black)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onPlayStopClick() },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            val playIcon = painterResource(R.drawable.ic_play_circle_green)
            val playIconDisabled = painterResource(R.drawable.ic_play_circle_disabled)
            val pauseIcon = painterResource(R.drawable.ic_pause)
            Icon(painter = if (isPlaying) pauseIcon
            else if(isCloseToLocation(99)){
                playIcon
            } else playIconDisabled,
                contentDescription = if (isPlaying) stringResource(id = R.string.pause_audio)
                else if(isCloseToLocation(99)){
                    stringResource(id = R.string.play_audio)
                } else stringResource(id = R.string.play_audio_disabled),
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp))
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
                    .width((progress * 100).dp)
            )
        }
    }
}

private fun isCloseToLocation(proximityValue: Int): Boolean = proximityValue < 100

@Composable
@Preview(showBackground = true)
fun BottomAudioPlayerRowPreview() {
    AudioBar(
        progress = 0.7f, // Valor de ejemplo para la barra de progreso (0.0f a 1.0f)
        isPlaying = true, // Valor de ejemplo para el estado de reproducción
        onPlayStopClick = { /* Lógica para alternar la reproducción/parada */ },
        locations = { Text(text = "Google Maps") }
    )
}
