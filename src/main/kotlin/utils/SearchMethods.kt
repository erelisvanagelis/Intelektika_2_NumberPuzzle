package utils

import kotlin.math.abs


fun totalHeuristic(scrambled: List<List<String>>, solved: List<List<String>>): Double {
    var sum = 0.0
    for (i in scrambled.indices) {
        for (j in scrambled.indices) {
//            println("i: $i, j: $j")
            val (x, y) = solved.findIndexes(scrambled[i][j])
            val distance = manhatanDistance(x, y, i, j)
            sum += distance
        }
    }
    return sum
}

fun manhatanDistance(i1: Int, j1: Int, i2: Int, j2: Int): Int {
    return abs(i1 - i2) + abs(j1 - j2)
}