package common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GridButtonContainer(
    oldGrid: List<List<String>>, newGrid: List<List<String>>, onButtonClick: (x: Int, y: Int) -> Unit
) {
    var color = Color(123, 31, 162)
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.border(width = 3.dp, color = color, shape = RoundedCornerShape(4)).padding(10.dp)
    ) {
        for (i in newGrid.indices) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                for (j in newGrid.indices) {

//                    if (newGrid[i][j] != oldGrid[i][j]){
//                        color = Color.Green
//                    }
                    if (newGrid[i][j] != ""){
                        GridButton(
                            value = newGrid[i][j],
                            x = i,
                            y = j,
                            onButtonClick = onButtonClick,
                            modifier = Modifier.width(width = 75.dp).height(height = 75.dp),
                            color = color
                        )
                    } else {
                        Box(modifier = Modifier.width(width = 75.dp).height(height = 75.dp))
                    }
                }
            }
        }
    }
}