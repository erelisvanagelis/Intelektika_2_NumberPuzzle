import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.*

class Game {
    var state by mutableStateOf(GameState())
        private set

    fun generateGrid (dimensionSize:Int) : List<List<String>>{
        val maxValue = dimensionSize * dimensionSize - 1
        val numbs: Queue<String> = LinkedList()
        numbs.add(" ")
        for (i in 1..maxValue) {
            numbs.add("$i")
        }

        val grid = mutableListOf<List<String>>()
        for (i in 1..dimensionSize){
            val row = mutableListOf<String>()
            for (j in 1..dimensionSize){
                row.add(numbs.remove())
            }
            grid.add(row)
        }

        state = GameState(currentArray = grid.toList())

        return grid
    }
    
    fun RandomizeGrid (gridSize:Int){
        val list = mutableListOf<Int>()
        val maxValue = gridSize * gridSize - 1
        for (i in 1..maxValue) {
            println(i)
        }
    }

    fun buttonPressed(x:Int, y:Int){
        println("x: $x, y: $y, value: ${state.currentArray[x][y]}")
    }
}