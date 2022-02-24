package common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GridButtonContainer(grid: List<List<String>>, onButtonClick: (x: Int, y: Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in grid.indices) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                for (j in grid.indices) {
                    GridButton(
                        value = grid[i][j],
                        x = i,
                        y = j,
                        onButtonClick = onButtonClick,
                        modifier = Modifier.width(width = 60.dp).height(height = 60.dp)
                    )
                }
            }
        }
    }
}