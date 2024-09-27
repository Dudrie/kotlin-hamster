package de.github.dudrie.hamster.ui.components.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.ui.generated.*
import de.github.dudrie.hamster.ui.model.UIViewModel
import org.jetbrains.compose.resources.imageResource

@Composable
fun AppBar(viewModel: UIViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.height(64.0.dp).fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppBarButton(onClick = {}) {
                Icon(
                    imageResource(Res.drawable.outline_undo_black_36dp),
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }
            AppBarButton(onClick = {}) {
                Icon(
                    imageResource(Res.drawable.round_play_arrow_black_36dp),
                    null,
                    modifier = Modifier.size(36.dp)
                )
            }
            AppBarButton(onClick = {}) {
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

            Spacer(Modifier.weight(1f))

            SpeedSlider(
                modifier = Modifier.widthIn(max = 400.dp).padding(end = 16.dp)
            )

            Text("Pausiert")
        }
    }
}
