package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.app_loading
import org.jetbrains.compose.resources.stringResource

@Composable
fun LadeUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.app_loading),
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.displaySmall
        )

        CircularProgressIndicator()
    }
}
