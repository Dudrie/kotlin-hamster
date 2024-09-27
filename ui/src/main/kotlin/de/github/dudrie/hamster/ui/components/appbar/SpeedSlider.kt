package de.github.dudrie.hamster.ui.components.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.speedometer
import de.github.dudrie.hamster.ui.generated.speedometer_slow
import de.github.dudrie.hamster.ui.model.UIViewModel
import org.jetbrains.compose.resources.imageResource

@Composable
fun SpeedSlider(modifier: Modifier = Modifier) {
    // TODO: Use speed from viewmodel
    val viewmodel = viewModel<UIViewModel>()
    val state by viewmodel.spielZustand.collectAsState()

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(imageResource(Res.drawable.speedometer_slow), null, modifier = Modifier.size(24.dp))

        Slider(
            value = state.geschwindigkeit.toFloat(),
            onValueChange = { viewmodel.setGeschwindigkeit(it) },
            valueRange = 1.0f..10.0f,
            modifier = Modifier.padding(4.dp).weight(1f, false),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        Icon(imageResource(Res.drawable.speedometer), null, modifier = Modifier.size(24.dp))
    }
}
