import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import utils.*

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
            generateList(maxValue).randomizeList(10),
            intArrayOf(dimensionSize, dimensionSize)
        )
        state = GameState(
            dimensionSize = dimensionSize,
            message = "Press Solve button to do the thing",
            solvedState = solved,
            currentState = scrambled,
        )
    }

    fun canSolvePuzzle(iterationLimit: String) {
        val value = iterationLimit.toIntOrNull()
        if (value == null) {
            state = GameState(message = "Grid size must be entered")
        }
        iterationLimit.toIntOrNull()?.let {
            if (it > 0) {
                solvePuzzle(it)
            } else {
                state = GameState(message = "Grid size > 2")
            }
        }
    }
    fun solvePuzzle(iterationLimit: Int) {
        state = GameState(
            message = "Searching for solution...",
            dimensionSize = state.dimensionSize,
            solvedState = state.solvedState,
            currentStep = state.currentStep,
            currentState = state.currentState,
        )
        try {
            val aStar = AStar(state.currentState, state.solvedState)
            val bestNode = aStar.search(iterationLimit)
//            val bestNode = stupidAStar(, iterationLimit = iterationLimit)
            var message = "Solution found"
            if (bestNode.heuristic != 0.0){
                message = "Iteration limit reached"
            }
            state = GameState(
                message = message,
                dimensionSize = state.dimensionSize,
                solvedState = state.solvedState,
                currentStep = bestNode.price,
                currentState = bestNode.current,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            state = GameState(
                message = "${e.localizedMessage} \n :(",
                dimensionSize = state.dimensionSize,
                solvedState = state.solvedState,
                currentStep = state.currentStep,
                currentState = state.currentState,
            )
        }
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


