package de.github.dudrie.hamster.ui.application.windows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.i18n.HamsterString

/**
 * UI shown while the game gets initially loaded.
 */
@Composable
fun LoadingUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = HamsterString.get("window.loading.text"),
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.h3
        )

        CircularProgressIndicator()
    }
}
