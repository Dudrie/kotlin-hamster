package de.github.dudrie.hamster.ui.components.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.ui.model.UIViewModel

@Composable
fun BoardForTerritory(modifier: Modifier = Modifier, viewModel: UIViewModel = viewModel()) {
    val spielState by viewModel.spielZustand.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BoardForTiles(
        kacheln = spielState.territorium.kacheln,
        hamster = spielState.territorium.hamster,
        highlightedTile = spielState.fehler?.position,
        hideHamster = uiState.hideHamster,
        mehrAlsEinHamster = spielState.territorium.hatMehrereHamster(),
        modifier = modifier
    )
}

internal const val BORDER_WIDTH = 1
