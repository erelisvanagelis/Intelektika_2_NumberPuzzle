data class GameState(
    val gridSize: Int = 0,
    val selectedAlgorithm: String = "",
    val numberOfSteps: Int = 0,
    val currentStep: Int = 0,
    val timeBetweenSteps: Int = 0,
    val totalTime: Int = 0,
    val currentArray: List<List<String>> = listOf(),
    )
