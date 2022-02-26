package utils

import models.Node
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

class AStar (val current: D2Array<Int>, val solved: D2Array<Int>){
    var frontierNodes: MutableList<Node> = mutableListOf(Node(current = current, solved = solved, price = 0, parent = null))
    val expandedNodes: MutableList<Node> = mutableListOf()
    var bestNode: Node = frontierNodes.first()

    fun search(iterationLimit: Int): Node {
        println("AStar - search")
        if (bestNode.heuristic == 0.0){
            return bestNode
        }

        var i:Int = 0
        while (frontierNodes.size != 0 && i < iterationLimit){
            val leaves = frontierNodes.first().generateLeaves()

            expandedNodes.add(frontierNodes.first())
            frontierNodes.removeFirst()

            leaves.forEach { placeLeaf(it) }

            frontierNodes.sortBy { it.f }

            if (frontierNodes.size > 0){
                println("frontierNodes.size= ${frontierNodes.size}, expandedNodes.size= ${expandedNodes.size}")
                println("BestLeaf: i= $i, price=${frontierNodes.first().price}, bestF= ${frontierNodes.first().f}, h=${frontierNodes.first().heuristic}")
            }

            i++
        }

        println("bestNode: price=${bestNode.price}, bestF= ${bestNode.f}, h=${bestNode.heuristic}")

        return bestNode
    }

    fun placeLeaf(node: Node){
        if (bestNode.f > node.f){
            bestNode = node
        }

        if (bestNode.heuristic == 0.0){
            return
        }

        val expandedMatch = expandedNodes.find { x -> x.current == node.current }
        val frontierMatch = frontierNodes.find { x -> x.current == node.current }

        if(expandedMatch == null && frontierMatch == null){
            frontierNodes.add(node)
        } else if (expandedMatch != null && frontierMatch == null && expandedMatch.price > node.price){
            frontierNodes.add(node)
        } else if (frontierMatch != null && frontierMatch.price > node.price){
            frontierNodes.remove(frontierMatch)
            frontierNodes.add(node)
        }
    }

    fun stupidAStar(iterationLimit: Int): Node {
        println("stupidAStar")
        var i:Int = 0
        while (frontierNodes.first().heuristic != 0.0 && i < iterationLimit){
            val leaves = frontierNodes.first().generateLeaves().toMutableList()
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

            expandedNodes.add(frontierNodes.first())
            frontierNodes.removeFirst()

            leaves.forEach {
                val found = frontierNodes.find { node -> it.current == node.current }
                if (found == null){
                    frontierNodes.add(it)
                } else if (found.price > it.price){
                    frontierNodes.remove(found)
                    frontierNodes.add(it)
                }
            }

            frontierNodes.addAll(leaves)
            frontierNodes = frontierNodes.sortedBy {it.f}.toMutableList()

            println("i= $i, price=${frontierNodes.first().price}, bestF= ${frontierNodes.first().f}, bestH=${frontierNodes.first().heuristic}")
            i++
        }
        return bestNode
    }
}