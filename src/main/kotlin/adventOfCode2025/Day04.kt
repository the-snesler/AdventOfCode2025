package adventOfCode2025
import org.apache.commons.lang3.math.NumberUtils.*
import util.*
import util.templates.Grid2D
import util.templates.Vector2D

@Suppress("unused")
class Day04(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        var grid = Grid2D.fromLines(input)

        fun isAccessible(e: Char, x: Int, y: Int): Boolean {
            return grid.getNeighbors(x, y, true).sumOf { if (grid[it] == '@') 1 else 0 } < 4 &&
                    grid[x, y] == '@'
        }

        a(grid.count(::isAccessible))
        var p2 = 0
        var breakOut = true
        while (true) {
            grid = grid.map { e, x, y ->
                if (isAccessible(e, x, y)) {
                    p2++
                    breakOut = false
                    '.'
                } else {
                    e
                }
            }
            if (!breakOut) breakOut = true
            else break
        }
        a(p2)
    }
}
