package adventOfCode2025
import util.*
import util.templates.Grid2D
import util.templates.Vector2D

@Suppress("unused")
class Day07(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        val grid = Grid2D.fromLines(input, '.')
        val p1 = mutableSetOf<Vector2D>()
        plt(grid.reduce { it, grid, x, y ->
            if ( it == '.' && x != 0 && y != 0 &&
                    grid[x - 1, y] == '^' && grid[x - 1, y - 1] == '|') {
                p1.add(Vector2D(x - 1, y))
                '|'
            } else if (it == '.' && x != grid.width - 1 && y != 0 &&
                    grid[x + 1, y] == '^' && grid[x + 1, y - 1] == '|') {
                p1.add(Vector2D(x + 1, y))
                '|'
            } else if (y != 0 && it != '^' && (grid[x, y-1] == 'S' || grid[x, y-1] == '|')) {
                    '|'
            } else it
        })
        a(p1.size)
        val p2 = Grid2D(grid.width, grid.height, 0L).reduce {
            _, intGrid, x, y ->
            val it = grid[x, y]
            var out = 0L
            if (it == '.' && x != 0 && y != 0 &&
                grid[x - 1, y] == '^') {
                out += intGrid[x - 1, y - 1]
            }
            if (it == '.' && x != grid.width - 1 && y != 0 &&
                grid[x + 1, y] == '^') {
                out += intGrid[x + 1, y - 1]
            }
            if (y != 0 && it != '^') out += intGrid[x, y - 1]
            if (it == 'S') out += 1
            out
        }
        a(p2.rows().last().sum())
        plt(p2.map { if (it == 0L) "-" else it.toString() })
    }
}
