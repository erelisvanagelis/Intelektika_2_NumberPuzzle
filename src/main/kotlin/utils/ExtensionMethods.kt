package utils

import models.Coordinates
import org.jetbrains.kotlinx.multik.ndarray.data.Dimension
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import kotlin.math.sqrt
import kotlin.random.Random

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}
fun MutableList<Int>.randomizeList(changeCount: Int): MutableList<Int> {
    val size = this.size
    val randomizedList = this.toMutableList()
    for (i in 0..changeCount){
        val x = Random.nextInt(0, size)
        val y = Random.nextInt(0, size)
        randomizedList.swap(x, y)
    }
    return randomizedList
}

fun <T, D : Dimension> NDArray<T, D>.findIndexes(t: T): Coordinates {
    val dimensionSize = sqrt(this.size.toDouble()).toInt()
    for (i in 0 until dimensionSize){
        for (j in 0 until dimensionSize){
            if (this.asD2Array()[i, j] == t){
                return Coordinates(i, j)
            }
        }
    }
    return Coordinates(-1, -1)
}

fun <T, D : Dimension> NDArray<T, D>.findBordering(i:Int, j:Int): List<Coordinates> {
    val dimensionSize = sqrt(this.size.toDouble()).toInt()
    val coordinatesList = mutableListOf<Coordinates>()
    if (i - 1 >= 0) {
        coordinatesList.add(Coordinates(i - 1, j))
    }
    if (i + 1 < dimensionSize) {
        coordinatesList.add(Coordinates(i + 1, j))
    }
    if (j - 1 >= 0) {
        coordinatesList.add(Coordinates(i, j - 1))
    }
    if (j + 1 < dimensionSize) {
        coordinatesList.add(Coordinates(i, j + 1))
    }

    return coordinatesList
}

fun <T, D : Dimension> NDArray<T, D>.swap(i: Int, j: Int, x: Int, y: Int) {
    val tmp = this.asD2Array()[i, j]
    this.asD2Array()[i, j] = this.asD2Array()[x, y]
    this.asD2Array()[x, y] = tmp
}