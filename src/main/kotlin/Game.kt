import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import utils.findBordering
import utils.findIndexes
import utils.randomizeList
import utils.swap

class Game {
    var state by mutableStateOf(GameState())
        private set

    fun generateList(maxValue: Int): MutableList<Int> {
        val list = mutableListOf<Int>()
        for (i in 0..maxValue) {
            list.add(i)
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
        val maxValue = dimensionSize * dimensionSize - 1
        val solved = mk.ndarray<Int, D2>(
            generateList(maxValue),
            intArrayOf(dimensionSize, dimensionSize)
        )
        val scrambled = mk.ndarray<Int, D2>(
            generateList(maxValue).randomizeList(100),
            intArrayOf(dimensionSize, dimensionSize)
        )
        state = GameState(
            dimensionSize = dimensionSize,
            message = "Press Solve button to do the thing",
            solvedState = solved,
            currentState = scrambled,
        )
    }

    fun solvePuzzle() {
        state = GameState(
            message = "Searching for solution...",
            dimensionSize = state.dimensionSize,
            solvedState = state.solvedState,
            currentStep = state.currentStep,
            currentState = state.currentState,
        )
//        try {
//            val node = Node(state.currentState.gridToMutable(), state.solvedState, price = 0)
//            val result = node.doTheThing()
//            println(result)
//        } catch (e: Exception) {
//            state = GameState(
//                message = "${e.localizedMessage} \n :(",
//                dimensionSize = state.dimensionSize,
//                solvedState = state.solvedState,
//                currentStep = state.currentStep,
//                currentState = state.currentState,
//            )
//        }
    }

    fun buttonPressed(x: Int, y: Int) {
        val blankCoordinates = state.currentState.findIndexes(0)
        val coordinates = state.currentState.findBordering(blankCoordinates.i, blankCoordinates.j)
        coordinates.find { coordinate -> coordinate.i == x && coordinate.j == y }?.let {
            val copy = state.currentState.copy()
            copy.swap(blankCoordinates.i, blankCoordinates.j, it.i, it.j)
            state = GameState(
                message = "Moving pieces",
                dimensionSize = state.dimensionSize,
                solvedState = state.solvedState,
                currentStep = state.currentStep + 1,
                currentState = copy,
            )
        }
    }
}


