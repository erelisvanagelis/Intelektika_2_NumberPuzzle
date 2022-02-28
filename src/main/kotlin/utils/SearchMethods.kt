package utils

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import kotlin.math.abs
import kotlin.math.sqrt


fun totalHeuristic(scrambled: D2Array<Int>, solved: D2Array<Int>): Double {
    var sum = 0.0
    val dimensionSize = sqrt(scrambled.size.toDouble()).toInt()
    for (i in 0 until dimensionSize) {
        for (j in 0 until dimensionSize) {
            val (x, y) = solved.findIndexes(scrambled[i, j])
            val distance = manhatanDistance(x, y, i, j)
            sum += distance
//            sum += dimensionSize * dimensionSize * distance
        }
    }
    return sum
}

fun manhatanDistance(i1: Int, j1: Int, i2: Int, j2: Int): Int {
    return abs(i1 - i2) + abs(j1 - j2)
}