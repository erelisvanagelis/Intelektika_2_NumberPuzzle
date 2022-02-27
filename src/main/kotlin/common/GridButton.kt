package common

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GridButton(
    value: String,
    x: Int,
    y: Int,
    onButtonClick: (x: Int, y: Int) -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onButtonClick(x, y) },
        border = BorderStroke(1.dp, color = color),
    ) {
        Text(text = value, fontSize = 30.sp)
    }
}