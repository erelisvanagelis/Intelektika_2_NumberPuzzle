package models

import utils.findIndexes
import utils.indexesBorderingValue
import utils.swapValues
import utils.totalHeuristic

class Node (val grid: MutableList<MutableList<String>>, val solved: List<List<String>>, val price: Int) {
    val heuristic: Double = totalHeuristic(grid, solved)
    val nodes = mutableListOf<Node>()
    var childrenGenerated = false

    fun generateChildren () {
        val (blankI, blankJ) = grid.findIndexes("")
        val coordinates = grid.indexesBorderingValue("")
        for(i in coordinates){
            val copy = grid.toMutableList()
            copy.swapValues(blankI, blankJ, i.i, i.j)
            nodes.add(Node(
                grid = copy,
                solved = solved,
                price = price + 1
            ))
        }
    }

    fun sortChildren(){
        nodes.sortBy{
            it.heuristic
        }
        nodes.forEach {
            println(it.heuristic)
        }
    }

    fun doTheThing() : String{
        if (heuristic == 0.0){
            return "completed"
        }

        if (!childrenGenerated){
            childrenGenerated = true
            generateChildren()
            sortChildren()
        }

        if (nodes.size == 0){
            return "end"
        }



        var result = nodes.first().doTheThing();
        if (result == "end"){
            nodes.removeFirst()
            result = doTheThing()
        }
        return result
    }
}