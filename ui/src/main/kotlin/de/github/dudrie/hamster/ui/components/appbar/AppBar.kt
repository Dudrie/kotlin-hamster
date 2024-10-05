package de.github.dudrie.hamster.ui.components.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.FullscreenExit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.game.SpielModus
import de.github.dudrie.hamster.ui.generated.*
import de.github.dudrie.hamster.ui.model.UIViewModel
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppBar(viewModel: UIViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val spielState by viewModel.spielZustand.collectAsState()

    Surface(
        modifier = Modifier.height(64.0.dp).fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppBarButton(
                onClick = { viewModel.ruckgangig() },
                enabled = spielState.kannRuckgangigMachen
            ) {
                Icon(
                    imageResource(Res.drawable.outline_undo_black_36dp),
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }
            AppBarButton(
                onClick = {
                    when {
                        spielState.kannWiederherstellen -> viewModel.stelleAlleWiederHer()
                        spielState.modus == SpielModus.Pausiert -> viewModel.setzeSpielFort()
                        else -> viewModel.pausiereSpiel()
                    }
                },
                enabled = spielState.modus == SpielModus.Lauft
                        || spielState.modus == SpielModus.Pausiert
                        || spielState.kannWiederherstellen
            ) {
                Icon(
                    painterResource(
                        when {
                            spielState.kannWiederherstellen -> Res.drawable.skip_next
                            spielState.modus == SpielModus.Pausiert -> Res.drawable.round_play_arrow_black_36dp
                            else -> Res.drawable.round_pause_black_36dp
                        }
                    ),
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }
            AppBarButton(
                onClick = { viewModel.stelleWiederHer() },
                enabled = spielState.kannWiederherstellen
            ) {
                Icon(
                    imageResource(Res.drawable.outline_redo_black_36dp),
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }

            AppBarButton(
                onClick = { viewModel.toggleHamsterVisibility() },
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Icon(
                    imageResource(if (uiState.hideHamster) Res.drawable.hamster_invisible else Res.drawable.hamster_visible),
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }

            AppBarButton(
                onClick = { viewModel.toggleConsoleVisibility() },
                modifier = Modifier.padding(start = 0.dp)
            ) {
                Icon(
                    if (uiState.showConsole) Icons.Rounded.Fullscreen else Icons.Rounded.FullscreenExit,
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            SpeedSlider(
                modifier = Modifier.widthIn(max = 400.dp).padding(end = 16.dp)
            )

            Text(
                text = stringResource(spielState.modus.getTextRes()),
                modifier = Modifier.widthIn(min = 90.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

private fun SpielModus.getTextRes(): StringResource = when (this) {
    SpielModus.Initialisierung -> Res.string.state_initializing
    SpielModus.Lauft -> Res.string.state_running
    SpielModus.Pausiert -> Res.string.state_paused
    SpielModus.Abgebrochen -> Res.string.state_aborted
    SpielModus.Gestoppt -> Res.string.state_stopped
}
