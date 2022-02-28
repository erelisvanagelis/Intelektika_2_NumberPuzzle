import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import utils.AStar
import utils.findBordering
import utils.findIndexes
import utils.swap
import kotlin.random.Random

class Game {
    private var uiScope = CoroutineScope(Dispatchers.Main)
    private var cpuScope = CoroutineScope(Dispatchers.IO)
    var state by mutableStateOf(GameState())
        private set

    private fun generateList(maxValue: Int): MutableList<Int> {
        val list = mutableListOf<Int>()
        for (i in 0..maxValue) {
            list.add(i)
        }
        return list
    }

    fun canScramblePuzzle(dimensionSize: String) {
        resetCoroutines()
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

    private fun scrambleByRules(array: D2Array<Int>, moves: Int): NDArray<Int, D2> {
        val clone = array.copy()
        for (u in 0..moves) {
            val (x, y) = clone.findIndexes(0)
            val coordinates = clone.findBordering(x, y)
            val randomIndex = Random.nextInt(0, coordinates.size)
            clone.swap(x, y, coordinates[randomIndex].i, coordinates[randomIndex].j)
        }
        return clone
    }

    private fun scramblePuzzle(dimensionSize: Int) {
        val maxValue = dimensionSize * dimensionSize - 1
        val solved = mk.ndarray<Int, D2>(
            generateList(maxValue),
            intArrayOf(dimensionSize, dimensionSize)
        )

        val scrambled = scrambleByRules(solved, dimensionSize * dimensionSize * 10)
        state = GameState(
            dimensionSize = dimensionSize,
            message = "Press Solve button to do the thing",
            solvedState = solved,
            currentState = scrambled,
            stepDelay = state.stepDelay
        )
    }

    fun canSolvePuzzle(iterationLimit: String) {
        resetCoroutines()
        val value = iterationLimit.toIntOrNull()
        if (value == null) {
            state = GameState(message = "Iteration limit must be entered")
        }
        iterationLimit.toIntOrNull()?.let {
            if (it > 0) {
                solvePuzzle(it)
            } else {
                state = GameState(
                    message = "Iteration limit > 0",
                    dimensionSize = state.dimensionSize,
                    solvedState = state.solvedState,
                    currentState = state.currentState,
                    stepDelay = state.stepDelay
                )
            }
        }
    }

    private fun solvePuzzle(iterationLimit: Int) {
        state = GameState(
            message = "Searching for solution...",
            dimensionSize = state.dimensionSize,
            solvedState = state.solvedState,
            currentStep = state.currentStep,
            stepCount = state.stepCount,
            currentState = state.currentState,
            stepDelay = state.stepDelay,
            isActive = true
        )

        val aStar = AStar(state.currentState, state.solvedState)
        val arrays = mutableListOf<D2Array<Int>>()

        cpuScope.launch {
            val bestNode = aStar.search(iterationLimit)
            val temp = mutableListOf<D2Array<Int>>()
            bestNode.findRootPath { temp.add(it) }
            var status = "Success"

            if (bestNode.heuristic != 0.0) {
                status = "Iteration limit"
            }
            state = GameState(
                message = status,
                dimensionSize = state.dimensionSize,
                solvedState = state.solvedState,
                currentStep = state.currentStep,
                stepCount = state.stepCount,
                stepDelay = state.stepDelay,
                currentState = state.currentState,
                status = status
            )
            temp.reverse()
            arrays.addAll(temp)
        }

        uiScope.launch {
            delay(1000)
            while (aStar.isActive && state.isActive) {
                val message =
                    "Iteration: ${aStar.i}, Percent: ${aStar.i.toFloat() / iterationLimit.toFloat() * 100}% \n" +
                            "Frontier Nodes: ${aStar.frontierNodes.size} \n" +
                            "Closest Node - F= ${aStar.closest.f}, G= ${aStar.closest.price}, H= ${aStar.closest.heuristic}"
                state = GameState(
                    message = message,
                    dimensionSize = state.dimensionSize,
                    solvedState = state.solvedState,
                    currentStep = state.currentStep,
                    stepCount = state.stepCount,
                    stepDelay = state.stepDelay,
                    currentState = state.currentState,
                    status = "Solving",
                    isActive = true,
                )
                delay(1000)
            }
            while (arrays.size == 0) {
                delay(1000)
            }
            val stepCount =  state.stepCount + arrays.size
            for (i in arrays) {
                state = GameState(
                    message = "Setting pieces",
                    dimensionSize = state.dimensionSize,
                    solvedState = state.solvedState,
                    currentStep = state.currentStep + 1,
                    stepCount = stepCount,
                    stepDelay = state.stepDelay,
                    currentState = i,
                    status = state.status
                )
                delay(state.stepDelay)
            }
        }
    }

    private fun resetCoroutines() {
        cpuScope.cancel()
        cpuScope = CoroutineScope(Dispatchers.IO)
        uiScope.cancel()
        uiScope = CoroutineScope(Dispatchers.Main)
    }

    fun setDelay(delay: Long) {
        state = GameState(
            message = "Delay set",
            dimensionSize = state.dimensionSize,
            solvedState = state.solvedState,
            currentStep = state.currentStep,
            stepCount = state.stepCount,
            currentState = state.currentState,
            stepDelay = delay,
        )
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
                stepDelay = state.stepDelay,
            )
        }
    }
}


