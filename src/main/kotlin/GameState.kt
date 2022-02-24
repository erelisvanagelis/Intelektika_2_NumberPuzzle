data class GameState(
    val dimensionSize: Int = 0,
    val totalSteps: Int = 0,
    val currentStep: Int = 0,
    val timeBetweenSteps: Int = 0,
    val totalTime: Int = 0,
    val oldState: List<List<String>> = listOf(),
    val currentState: List<List<String>> = listOf(),
    val solvedState: List<List<String>> = listOf(),
    val heuristic: Double = 0.0,
    )
