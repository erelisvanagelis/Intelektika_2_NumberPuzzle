import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import utils.findIndexes
import utils.randomizeList
import utils.toGrid
import java.util.*
import kotlin.math.abs

class Game {
    var state by mutableStateOf(GameState())
        private set

    fun generateList(maxValue: Int): MutableList<String> {
        val list = mutableListOf<String>()
        list.add("")
        for (i in 1..maxValue) {
            list.add("$i")
        }
        return list
    }

    fun scramblePuzzle(dimensionSize: Int) {
        val solved = generateList(dimensionSize * dimensionSize - 1).toGrid(dimensionSize)
        val scrambled = generateList(dimensionSize * dimensionSize - 1)
        scrambled.randomizeList(dimensionSize * 100)
        val randomizedGrid = scrambled.toGrid(dimensionSize)
        state = GameState(
            dimensionSize = dimensionSize,
            heuristic = totalHeuristic(randomizedGrid, solved),
            solvedState = solved,
            currentState = randomizedGrid,
            oldState = randomizedGrid,
        )
        println(state)
    }

    fun solvePuzzle(){
//        val test1 = state.currentState[0][0]
//        val (i, j) = state.solvedState.findIndexes(test1)
//        val distance = manhatanDistance(0, 0)

    }

    fun totalHeuristic(scrambled: List<List<String>>, solved: List<List<String>>) : Double{
        var sum = 0.0
        for (i in scrambled.indices){
            for (j in scrambled.indices){
                println("i: $i, j: $j")
                val (x, y) = solved.findIndexes(scrambled[i][j])
                val distance = manhatanDistance(x, y, i, j)
                sum += distance
            }
        }
        return sum
    }

    fun manhatanDistance(i1:Int, j1:Int, i2:Int, j2:Int): Int {
        return abs(i1 - i2) + abs(j1 - j2)
    }


    fun buttonPressed(x: Int, y: Int) {
        val (i, j) = state.solvedState.findIndexes(state.currentState[x][y])
        val distance = manhatanDistance(x, y, i, j)
        println("x: $x, y: $y, i: $i, j: $j, value: ${state.currentState[x][y]}, distance: $distance")
    }
}