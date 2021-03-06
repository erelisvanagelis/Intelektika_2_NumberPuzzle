package common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get

@Composable
fun GridButtonContainer(
    grid: D2Array<Int>, onButtonClick: (x: Int, y: Int) -> Unit
) {
    val color = Color(123, 31, 162)
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.border(width = 3.dp, color = color, shape = RoundedCornerShape(2)).padding(10.dp)
    ) {
        val dimensionSize = grid[0].size
        val weight = 1f / dimensionSize
        for (i in 0 until dimensionSize) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize().weight(weight)) {
                for (j in 0 until dimensionSize) {
                    if (grid[i, j] != 0) {
                        GridButton(
                            value = "${grid[i, j]}",
                            x = i,
                            y = j,
                            onButtonClick = onButtonClick,
                            modifier = Modifier.fillMaxSize().weight(weight),
                            color = color
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxSize().weight(weight))
                    }
                }
            }
        }
    }
}