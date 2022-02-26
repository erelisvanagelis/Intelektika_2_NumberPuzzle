package utils

import models.Node
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
        }
    }
    return sum
}

fun manhatanDistance(i1: Int, j1: Int, i2: Int, j2: Int): Int {
    return abs(i1 - i2) + abs(j1 - j2)
}

fun stupidAStar(current: D2Array<Int>, solved: D2Array<Int>, iterationLimit: Int): Node {
    println("stupidAStar")
    var frontiers: MutableList<Node> = mutableListOf(Node(current = current, solved = solved, price = 0, parent = null))
    var i:Int = 0
    val heuristic = totalHeuristic(scrambled = current, solved = solved)
    var bestNode: Node = frontiers.first()
    while (frontiers.first().heuristic != 0.0 && i < iterationLimit){
        val leaves = frontiers.first().generateLeaves().toMutableList()
        val sortedLeaves = leaves.sortedBy { it.heuristic }
        val closest = sortedLeaves.first()
        if (closest.heuristic ==0.0){
            bestNode = closest
            break
        } else if (closest.heuristic < bestNode.heuristic){
            bestNode = closest
        } else if (closest.heuristic == bestNode.heuristic && closest.price < bestNode.price){
            bestNode = closest
        }

        frontiers.removeFirst()
        frontiers.addAll(leaves)
        frontiers = frontiers.sortedBy {it.f}.toMutableList()

        println("i= $i, price=${frontiers.first().price}, bestF= ${frontiers.first().f}, bestH=${frontiers.first().heuristic}")
        i++
    }
//    frontiers.sortedBy { it.heuristic }
    println("i= $i, price=${frontiers.first().price}, bestF= ${frontiers.first().f}, bestH=${frontiers.first().heuristic}")

//    return "i= $i, price=${frontiers.first().price}, bestF= ${frontiers.first().f}, bestH=${frontiers.first().heuristic}"
    return bestNode
}