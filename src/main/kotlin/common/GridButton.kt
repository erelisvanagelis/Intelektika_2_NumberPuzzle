package common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GridButton(value: String, x: Int, y: Int, onButtonClick: (x: Int, y: Int) -> Unit, modifier: Modifier = Modifier) {
        Button(
            onClick = { onButtonClick(x, y) },
            modifier = modifier.padding(4.dp)
        ) {
            Text(value)
        }
}