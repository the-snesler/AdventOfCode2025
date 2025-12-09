package adventOfCode2025
import util.*
import util.templates.Grid2D

@Suppress("unused")
class Day06(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        val splitLines = lines.map { it.split(" ").filter { it.isNotBlank() } }
        val problemCt = splitLines[0].size
        val rowCt = splitLines.lastIndex
        val numbers = splitLines.subList(0, rowCt)
        val problemDigits = mutableListOf<Int>()
        var p1 = 0L
        for (i in 0 until problemCt) {
            val out = when (splitLines[rowCt][i]) {
                "+" -> numbers.sumOf { it[i].toLong() }
                "*" -> numbers.fold(1L) { acc, strings -> acc * strings[i].toLong() }
                else -> 0L
            }
            plt(out, numbers.map { it[i] })
            problemDigits.add(splitLines.maxOf { it[i].length })
            p1 += out
        }
        a(p1)

        var p2 = 0L
        var i = 0
        var marker = 0
        while (marker < lines[0].length) {
            val rows = lines.subList(0, rowCt).map { it.subSequence(marker, marker +
                    problemDigits[i]).map { if (it == ' ') -1 else it.code - '0'.code }.toList() }
            val grid = Grid2D.fromLines( rows, -1).transpose()
            plt(grid)
            plt()
            val numbers = grid.rows().map {
                it.reversed().fold(Pair(0, 1)) {
                    acc, int -> if (int != -1) Pair(acc.first + acc.second * int, acc.second * 10) else acc
                }.first.toLong()
            }
            p2 += when (splitLines[rowCt][i]) {
                "+" -> numbers.sum()
                "*" -> numbers.fold(1L) { acc, int -> acc * int }
                else -> 0L
            }
            marker += problemDigits[i] + 1
            i++
        }
        a(p2)
    }
}
