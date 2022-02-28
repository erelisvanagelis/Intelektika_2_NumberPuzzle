package utils

import models.Node
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

class AStar(current: D2Array<Int>, solved: D2Array<Int>) {
    var frontierNodes: MutableList<Node> =
        mutableListOf(Node(current = current, solved = solved, price = 0, parent = null))
    val expandedNodes: MutableList<Node> = mutableListOf()
    var bestNode: Node = frontierNodes.first()
    var isActive = false
    var i = 0

    fun search(iterationLimit: Int): Node {
        println("AStar - search, iterationLimit = $iterationLimit")
        isActive = true
        i = 0
        while (frontierNodes.size != 0 && i < iterationLimit) {
            val first = frontierNodes.first()
            if(bestNode.heuristic == 0.0 && bestNode.f <= first.f){
                break
            }

            val leaves = first.generateLeaves()
            expandedNodes.add(first)
            frontierNodes.removeFirst()
            leaves.forEach { placeLeaf(it) }

            if (frontierNodes.size > 0) {
                println("frontierNodes.size= ${frontierNodes.size}, expandedNodes.size= ${expandedNodes.size}")
                println("BestLeaf: i= $i, price=${frontierNodes.first().price}, bestF= ${frontierNodes.first().f}, h=${frontierNodes.first().heuristic}")
            }
            i++
        }

        println("bestNode: price=${bestNode.price}, bestF= ${bestNode.f}, h=${bestNode.heuristic}")
        isActive = false
        return bestNode
    }

    fun placeLeaf(node: Node) {
        if (bestNode.f > node.f) {
            bestNode = node
            return
        }
        insertIntoOrderedList(frontierNodes, node)
    }

    fun insertIntoOrderedList(list: MutableList<Node>, node: Node){
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