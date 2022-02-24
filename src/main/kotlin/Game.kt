import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import utils.randomizeList
import utils.toGrid
import java.util.*

class Game {
    var state by mutableStateOf(GameState())
        private set

    fun generateList(maxValue: Int): MutableList<String> {
        val list = mutableListOf<String>()
        list.add(" ")
        for (i in 1..maxValue) {
            list.add("$i")
        }
        return list
    }

    fun scramblePuzzle(dimensionSize: Int) {
        val solved = generateList(dimensionSize * dimensionSize - 1)
        val scrambled = generateList(dimensionSize * dimensionSize - 1)
        scrambled.randomizeList(dimensionSize * 100)
        state = GameState(
            dimensionSize = dimensionSize,
            solvedState = solved.toGrid(dimensionSize),
            currentState = scrambled.toGrid(dimensionSize),
            oldState = scrambled.toGrid(dimensionSize),
        )
    }

    fun solvePuzzle(){
        val test1 = state.currentState[0][0]
//        state.solvedState.find {  }

    }



    fun buttonPressed(x: Int, y: Int) {
        println("x: $x, y: $y, value: ${state.currentState[x][y]}")
    }
}