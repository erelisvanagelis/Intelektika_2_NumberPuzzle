package utils

import models.Node
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

class AStar(current: D2Array<Int>, solved: D2Array<Int>) {
    var frontierNodes: MutableList<Node> =
        mutableListOf(Node(current = current, solved = solved, price = 0, parent = null))
    var isActive = false
    var i = 0
    var closest = frontierNodes.first()
    fun search(iterationLimit: Int): Node {
        println("AStar - search, iterationLimit = $iterationLimit")
        isActive = true
        i = 0
        while (frontierNodes.size != 0 && i < iterationLimit) {
            val first = frontierNodes.first()
            if (first.heuristic == 0.0){
                closest = first
                break
            }
            if (first.heuristic < closest.heuristic){
                closest = first
            }

            val leaves = first.generateLeaves()
            frontierNodes.removeFirst()
            leaves.forEach {
                insertIntoOrderedList(frontierNodes, it)
            }

            if (frontierNodes.size > 0) {
                println("frontierNodes.size= ${frontierNodes.size}")
                println("BestLeaf: i= $i, price=${frontierNodes.first().price}, bestF= ${frontierNodes.first().f}, h=${frontierNodes.first().heuristic}")
            }
            i++
        }

        isActive = false
        return closest
    }

    private fun insertIntoOrderedList(list: MutableList<Node>, node: Node){
        var added = false
        for (i in list.indices){
            if (list[i].f > node.f){
                list.add(i, node)
                added = true
                break
            }
        }

        if (!added){
            list.add(node)
        }
    }
}