data class GameState(
    val dimensionSize: Int = 0,
    val message: String = "Press Scramble button to show the puzzle",
    val totalSteps: Int = 0,
    val currentStep: Int = 0,
    val timeBetweenSteps: Int = 0,
    val totalTime: Int = 0,
    val oldState: List<List<String>> = listOf(),
    val currentState: List<List<String>> = listOf(),
    val solvedState: List<List<String>> = listOf(),
    val heuristic: Double = 0.0,
    )
