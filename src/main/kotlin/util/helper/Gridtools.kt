package util.helper

import util.templates.Grid2D
import util.templates.Vector2D
import java.util.PriorityQueue
import kotlin.collections.ArrayDeque

object Gridtools {
    /**
     * Returns a map of all reachable nodes from start, and the direction to get there
     * https://www.redblobgames.com/pathfinding/a-star/implementation.html#python-early-exit
     * @param end If not null, the search will stop when this node is reached
     * @return A map of all reachable nodes from start, and the direction to get there
     */
    private fun mapBreadthFirstSearch(grid: Grid2D<Boolean>, start: Vector2D, end: Vector2D?): Map<Vector2D, Vector2D?> {
        val queue = ArrayDeque<Vector2D>()
        queue.add(start)
        val reached = mutableSetOf(start)
        val fromDirection = mutableMapOf<Vector2D, Vector2D?>(start to null)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current == end) break
            for (next in grid.getAltNeighbors(current)) {
                if (next !in reached && grid[next]) {
                    queue.addLast(next)
                    reached.add(next)
                    fromDirection[next] = current - next
                }
            }
        }
        return fromDirection
    }

    /**
     * Returns a list of nodes from start to end, or an empty list if no path exists
     */
    fun breadthFirstSearch(grid: Grid2D<Boolean>, start: Vector2D, end: Vector2D): List<Vector2D> {
        val fromDirection = mapBreadthFirstSearch(grid, start, end)
        val path = mutableListOf<Vector2D>()
        var current = end
        while (current != start) {
            path.add(current)
            current += fromDirection[current] ?: return emptyList()
        }
        path.add(start)
        path.reverse()
        return path
    }

    /**
     * Trick to make movement prettier on even-weight grids.
     * Pretend the grid is a checkerboard (such that a state toggles when moving between cells)
     *  when true: make horizontal movement slightly more expensive
     *  when false: make vertical movement slightly more expensive
     * Encourages tasty zigzags instead of boring straight lines
     */
    private fun pathNudge(v1: Vector2D, v2: Vector2D): Int {
        val checkerboard = (v2.x + v2.y) % 2 == 0
        if (checkerboard && v2.x != v1.x) return 1
        if (!checkerboard && v2.y != v1.y) return 1
        return 0
    }

    /**
     * Uses Dijkstra's algorithm to find the shortest path from start to end using an integer weighted grid
     * https://www.redblobgames.com/pathfinding/a-star/implementation.html#python-search
     * @return a Pair with 1) the direction field and 2) cost field found by the algorithm
     */
    fun mapDijkstrasAlgoSearch(grid: Grid2D<Int>, start: Vector2D, end: Vector2D?, useNudge: Boolean = true):
            Pair<Map<Vector2D, Vector2D?>, Map<Vector2D, Int>> {
        val queue = PriorityQueue<Pair<Vector2D, Float>>(compareBy { it.second })
        queue.add(Pair(start, 0f))
        val fromDirection = mutableMapOf<Vector2D, Vector2D?>(start to null)
        val costSoFar = mutableMapOf(start to 0f)
        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (current.first == end) break
            for (next in grid.getNeighbors(current.first)) {
                val nudge = if (useNudge) 0.001f * pathNudge(current.first, next) else 0f
                val newCost = costSoFar[current.first]!! + nudge + grid[next]
                if (newCost < costSoFar.getOrDefault(next, Float.POSITIVE_INFINITY)) {
                    costSoFar[next] = newCost
                    queue.add(Pair(next, newCost))
                    fromDirection[next] = current.first - next
                }
            }
        }
        return Pair(fromDirection, costSoFar.mapValues { it.value.toInt() })
    }

    fun dijkstrasAlgoSearch(grid: Grid2D<Int>, start: Vector2D, end: Vector2D): Iterable<Vector2D> {
        val fromDirection = mapDijkstrasAlgoSearch(grid, start, end).first
        val path = mutableListOf<Vector2D>()
        var current = end
        while (current != start) {
            path.add(current)
            current += fromDirection[current] ?: return emptyList()
        }
        path.add(start)
        path.reverse()
        return path
    }
}
