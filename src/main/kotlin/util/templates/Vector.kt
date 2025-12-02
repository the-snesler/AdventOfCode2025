package util.templates

import kotlin.math.abs
import kotlin.math.sqrt

class Vector2D(val x: Int, val y: Int) {
    companion object {
        val NORTH = Vector2D(0, 1)
        val SOUTH = Vector2D(0, -1)
        val EAST = Vector2D(1, 0)
        val WEST = Vector2D(-1, 0)
        val CARDDIRS = setOf(NORTH, SOUTH, EAST, WEST)
    }

    operator fun plus(v: Vector2D): Vector2D {
        return Vector2D(x + v.x, y + v.y)
    }

    operator fun minus(v: Vector2D): Vector2D {
        return this + (v * -1)
    }

    operator fun times(i: Int): Vector2D {
        return Vector2D(x * i, y * i)
    }

    fun copy(): Vector2D {
        return Vector2D(x, y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector2D) return false
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    fun manhattanDistance(): Int {
        return abs(x) + abs(y)
    }

    fun manhattanDistance(g2: Vector2D): Int {
        return abs(g2.x - x) + abs(g2.y - y)
    }

    /**
     * If this vector is a cardinal direction, return the char representation
     */
    fun toChar(): Char {
        return when (this) {
            NORTH -> 'v'
            SOUTH -> '^'
            EAST -> '>'
            WEST -> '<'
            else -> '.'
        }
    }

    fun rotLeft(): Vector2D {
        return Vector2D(-y, x)
    }

    fun rotRight(): Vector2D {
        return Vector2D(y, -x)
    }

    fun magnitude(): Double {
        return sqrt((x * x + y * y).toDouble())
    }

    fun isParallel(v: Vector2D): Boolean {
        return (x * v.y - y * v.x) == 0
    }

    operator fun component1(): Int {
        return x
    }

    operator fun component2(): Int {
        return y
    }
}
