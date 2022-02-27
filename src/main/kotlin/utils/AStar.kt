package utils

import models.Node
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

class AStar(current: D2Array<Int>, solved: D2Array<Int>) {
    var frontierNodes: MutableList<Node> =
        mutableListOf(Node(current = current, solved = solved, price = 0, parent = null))
    val expandedNodes: MutableList<Node> = mutableListOf()
    var bestNode: Node = frontierNodes.first()
    var isActive = false

    fun search(iterationLimit: Int): Node {
        println("AStar - search")
        if (bestNode.heuristic == 0.0) {
            return bestNode
        }
        isActive = true

        var i = 0
        while (frontierNodes.size != 0 && i < iterationLimit) {
            val leaves = frontierNodes.first().generateLeaves()

            val expandedNode = expandedNodes.find { x -> x.current == frontierNodes.first().current }
            if (expandedNode == null) {
                expandedNodes.add(frontierNodes.first())
            } else if (expandedNode.f > frontierNodes.first().f) {
                expandedNodes.add(frontierNodes.first())
            }
            frontierNodes.removeFirst()

            leaves.forEach { placeLeaf(it) }

            frontierNodes.sortBy { it.f }

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
        }

        if (node.heuristic == 0.0) {
            val filtered = frontierNodes.filter { x -> x.price < node.price }
            frontierNodes = filtered.toMutableList()
            return
        }

        if (bestNode.heuristic == 0.0 && bestNode.f <= node.f) {
            expandedNodes.add(node)
            return
        }

        val expandedMatch = expandedNodes.find { x -> x.current == node.current }
        val frontierMatch = frontierNodes.find { x -> x.current == node.current }



        if (expandedMatch == null && frontierMatch == null) {
            frontierNodes.add(node)
        } else if (expandedMatch != null && frontierMatch == null && expandedMatch.price > node.price) {
            frontierNodes.add(node)
        } else if (frontierMatch != null && frontierMatch.price > node.price) {
            frontierNodes.remove(frontierMatch)
            frontierNodes.add(node)
        }
    }

}