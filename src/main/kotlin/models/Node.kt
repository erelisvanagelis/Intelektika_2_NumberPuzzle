package models

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import utils.findBordering
import utils.findIndexes
import utils.swap
import utils.totalHeuristic

class Node(val current: D2Array<Int>, val solved: D2Array<Int>, var price: Int, val parent: Node?) {
    val heuristic: Double = totalHeuristic(current, solved)
    val f = price + heuristic

    fun generateLeaves(): List<Node> {
        val leaves = mutableListOf<Node>()
        val blankCoordinates = current.findIndexes(0)
        val coordinates = current.findBordering(blankCoordinates.i, blankCoordinates.j)
        for (i in coordinates) {
            val copy = current.copy()
            copy.swap(blankCoordinates.i, blankCoordinates.j, i.i, i.j)
            if (copy != parent?.current){
                leaves.add(
                    Node(
                        current = copy,
                        solved = solved,
                        price = price + 1,
                        parent = this,
                    )
                )
            }
        }
        return leaves
    }

    fun recalculatePrice(){

    }

    fun findRoot(addState: (D2Array<Int>) -> Unit){
        addState(current)
        parent?.let{
            parent.findRoot(addState)
        }
    }
}