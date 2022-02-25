import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.Node
import utils.*

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

    fun canScramblePuzzle(dimensionSize: String) {
        val value = dimensionSize.toIntOrNull()
        if (value == null) {
            state = GameState(message = "Grid size must be entered")
        }
        dimensionSize.toIntOrNull()?.let {
            if (it > 2) {
                scramblePuzzle(it)
            } else {
                state = GameState(message = "Grid size > 2")
            }
        }
    }

    fun scramblePuzzle(dimensionSize: Int) {
        val solved = generateList(dimensionSize * dimensionSize - 1).toGrid(dimensionSize)
        val scrambled = generateList(dimensionSize * dimensionSize - 1)
        scrambled.randomizeList(dimensionSize * 100)
        val randomizedGrid = scrambled.toGrid(dimensionSize)
        state = GameState(
            dimensionSize = dimensionSize,
            message = "Press Solve button to do the thing",
            heuristic = totalHeuristic(randomizedGrid, solved),
            solvedState = solved,
            currentState = randomizedGrid,
            oldState = randomizedGrid,
        )
        println(state)
    }

    fun solvePuzzle() {
        state = GameState(
            message = "Searching for solution...",
            oldState = state.currentState,
            dimensionSize = state.dimensionSize,
            solvedState = state.solvedState,
            heuristic = state.heuristic,
            currentStep = state.currentStep,
            currentState = state.currentState,
        )
        val node = Node(state.currentState.gridToMutable(), state.solvedState, price = 0)
        val result = node.doTheThing()
        println(result)

    }

    fun buttonPressed(x: Int, y: Int) {
        val (i, j) = state.solvedState.findIndexes(state.currentState[x][y])
        val distance = manhatanDistance(x, y, i, j)
        println("x: $x, y: $y, i: $i, j: $j, value: ${state.currentState[x][y]}, distance: $distance")

        val (blankI, blankJ) = state.currentState.findIndexes("")
        val coordinates = state.currentState.indexesBorderingValue("")
        coordinates.find { coordinate -> coordinate.i == x && coordinate.j == y }?.let {
            val swapped = state.currentState.gridToMutable()
            swapped.swapValues(x, y, blankI, blankJ)
            state = GameState(
                message = "Moving pieces",
                oldState = state.currentState,
                dimensionSize = state.dimensionSize,
                solvedState = state.solvedState,
                heuristic = state.heuristic,
                currentStep = state.currentStep + 1,
                currentState = swapped,
            )
        }
    }
}