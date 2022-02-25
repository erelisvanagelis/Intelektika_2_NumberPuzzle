package utils

import models.Coordinates
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

fun MutableList<MutableList<String>>.swapValues(i1: Int, j1: Int, i2: Int, j2: Int) {
    val tmp = this[i1][j1]
    this[i1][j1] = this[i2][j2]
    this[i2][j2] = tmp
}

fun List<List<String>>.findIndexes(value: String) : Coordinates{
    val dimensionSize = this.size
    for (i in 0 until dimensionSize){
        for (j in 0 until dimensionSize){
            if (this[i][j] == value){
                return Coordinates(i, j)
            }
        }
    }
    return Coordinates(-1, -1)
}

fun List<List<String>>.gridToMutable() : MutableList<MutableList<String>> {
    val dimensionSize = this.size
    val mutableList = mutableListOf<MutableList<String>>()
    for (i in 0 until dimensionSize){
        mutableList.add(this[i].toMutableList())
    }
    return mutableList
}

fun List<List<String>>.indexesBorderingValue(value: String): MutableList<Coordinates> {
    val coordinatesList = mutableListOf<Coordinates>()
    val (i, j) = this.findIndexes(value)
    if (i - 1 >= 0) {
        coordinatesList.add(Coordinates(i - 1, j))
    }
    if (i + 1 < this.size) {
        coordinatesList.add(Coordinates(i + 1, j))
    }
    if (j - 1 >= 0) {
        coordinatesList.add(Coordinates(i, j - 1))
    }
    if (j + 1 < this.size) {
        coordinatesList.add(Coordinates(i, j + 1))
    }

    return coordinatesList
}

//fun MutableList<MutableList<String>>.indexesBorderingValue(value: String): MutableList<Coordinates> {
//    val coordinatesList = mutableListOf<Coordinates>()
//    val (i, j) = this.findIndexes(value)
//    if (i - 1 >= 0) {
//        coordinatesList.add(Coordinates(i - 1, j))
//    }
//    if (i + 1 < this.size) {
//        coordinatesList.add(Coordinates(i + 1, j))
//    }
//    if (j - 1 >= 0) {
//        coordinatesList.add(Coordinates(i, j - 1))
//    }
//    if (j + 1 < this.size) {
//        coordinatesList.add(Coordinates(i, j + 1))
//    }
//
//    return coordinatesList
//}