package adventOfCode2025
import util.*
import kotlin.collections.toList
import kotlin.collections.zip
import kotlin.text.split
import kotlin.text.toInt


@Suppress("unused")
class Day08(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        val iterations = if (context == RunContext.TEST) 10 else 1000
        val points = lines.map { it.split(',').map { it.toInt() }.toIntArray() }
        data class Edge(val dist: Long, val a: Int, val b: Int)

        val allEdges = buildList {
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val d = points[i].zip(points[j]).sumOf { (x, y) -> (x - y).toLong().let { it * it } }
                    add(Edge(d, i, j))
                }
            }
        }.sortedBy { it.dist }
        plt(allEdges.size, allEdges.take(10))

        // Union-Find
        val parent = IntArray(points.size) { it }
        fun find(x: Int): Int {
            if (parent[x] != x) parent[x] = find(parent[x])
            return parent[x]
        }
        fun union(a: Int, b: Int): Boolean {
            val ra = find(a)
            val rb = find(b)
            if (ra == rb) return false
            parent[rb] = ra
            return true
        }

        var i = 0
        var connected = 0
        val target = lines.size

        for (edge in allEdges) {
            val result = union(edge.a, edge.b)
            i++
            // plt("Connected", points[edge.a].toList(), "to", points[edge.b].toList())
            if (i == iterations - 1) {
                val circuits = points.indices.groupBy { find(it) }.values.map { it.size }
                plt(circuits.sortedDescending())
                a(circuits.sortedDescending().take(3).reduce(Int::times))
            }
            if (result && ++connected == target - 1) {
                a(1L * points[edge.a][0] * points[edge.b][0])
                return
            }
        }
    }
}

