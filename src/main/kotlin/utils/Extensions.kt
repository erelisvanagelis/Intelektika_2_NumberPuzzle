package utils

import kotlin.random.Random

fun MutableList<String>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}
fun MutableList<String>.randomizeList(changeCount: Int){
    val size = this.size
    for (i in 0..changeCount){
        val x = Random.nextInt(0, size)
        val y = Random.nextInt(0, size)
        this.swap(x, y)
    }
}

fun MutableList<String>.toGrid(dimensionSize:Int) : MutableList<MutableList<String>> {
    val grid: MutableList<MutableList<String>> = mutableListOf()
    val copy = this.toMutableList()
    for (i in 1..dimensionSize){
        val row: MutableList<String> = mutableListOf()
        for (j in 1..dimensionSize){
            if (copy.size == 0){
                break
            }
            row.add(copy.first())
            copy.removeFirst()
        }
        grid.add(row)
    }
    return grid
}