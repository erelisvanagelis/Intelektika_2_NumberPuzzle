package utils

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import kotlin.math.abs


fun totalHeuristic(scrambled: D2Array<Int>, solved: D2Array<Int>): Double {
    var sum = 0.0
    for (i in scrambled.indices) {
        for (j in scrambled.indices) {
            val (x, y) = solved.findIndexes(scrambled[i, j])
            val distance = manhatanDistance(x, y, i, j)
            sum += distance
        }
    }
    return sum
}

fun manhatanDistance(i1: Int, j1: Int, i2: Int, j2: Int): Int {
    return abs(i1 - i2) + abs(j1 - j2)
}