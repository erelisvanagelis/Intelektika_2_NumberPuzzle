import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

data class GameState(
    val isActive: Boolean = false,
    val dimensionSize: Int = 3,
    val message: String = "Press Scramble button to show the puzzle",
    val status: String = "None",
    val stepCount: Int = 0,
    val currentStep: Int = 0,
    val stepDelay: Long = 500,
    val currentState: D2Array<Int> = mk.zeros(dimensionSize, dimensionSize),
    val solvedState: D2Array<Int> = mk.zeros(dimensionSize, dimensionSize),
    )
